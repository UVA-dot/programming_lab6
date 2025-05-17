import managers.CollectionManager;
import managers.Controller;
import managers.commands.Exit;
import models.Dragon;
import models.DragonType;
import models.DragonCharacter;
import models.Coordinates;
import models.DragonHead;
import response.CommandResponse;

import java.io.*;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Scanner;
import java.time.LocalDateTime;

public class Main {
    public static int PORT = 2024;

    public static void main(String[] args) throws IOException, ClassNotFoundException, ConnectException {
        try {
            SocketChannel channel = SocketChannel.open();
            channel.connect(new InetSocketAddress("localhost", 5050));
            channel.configureBlocking(false);
            Controller controller = new Controller();
            System.out.println("Успешное подключение к серверу");
            
            // Показываем список команд один раз при запуске
            controller.run();
            
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("Введите команду: ");
                String userInput = scanner.nextLine().trim();
                
                if (userInput.isEmpty()) continue;
                
                String[] parts = userInput.split("\\s+", 2);
                String command = parts[0].toLowerCase();
                String argument = parts.length > 1 ? parts[1] : "";
                
                // Создаем Dragon для команд, которые его требуют
                Dragon dragon = null;
                if (command.equals("add") || command.equals("add_if_max") || command.equals("update")) {
                    dragon = createDragon(scanner);
                    if (dragon == null) {
                        System.out.println("Не удалось создать дракона");
                        continue;
                    }
                    if (command.equals("update")) {
                        try {
                            long id = Long.parseLong(argument);
                            CollectionManager.request = new request.CommandRequest(command, id, dragon);
                        } catch (NumberFormatException e) {
                            System.out.println("Неверный формат ID");
                            continue;
                        }
                    } else {
                        // Для команд add и add_if_max используем id = 0, так как id генерируется на сервере
                        CollectionManager.request = new request.CommandRequest(command, 0, dragon);
                    }
                } else if (command.equals("remove_by_id")) {
                    try {
                        long id = Long.parseLong(argument);
                        CollectionManager.request = new request.CommandRequest(command, id);
                    } catch (NumberFormatException e) {
                        System.out.println("Неверный формат ID");
                        continue;
                    }
                } else {
                    // Для остальных команд просто передаем имя команды
                    CollectionManager.request = new request.CommandRequest(command);
                }
                
                if (!CollectionManager.request.getName().equals("skip")) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject(CollectionManager.request);
                    oos.flush();
                    byte[] requestBytes = baos.toByteArray();
                    ByteBuffer requestBuffer = ByteBuffer.wrap(requestBytes);
                    channel.write(requestBuffer);

                    printer(channel);
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.out.println("Не удалось подключиться");
        }
    }

    private static Dragon createDragon(Scanner scanner) {
        try {
            System.out.println("Введите данные для дракона:");
            
            System.out.print("Имя: ");
            String name = scanner.nextLine();
            
            System.out.print("Координата X (должна быть меньше 353): ");
            long x = Long.parseLong(scanner.nextLine());
            
            System.out.print("Координата Y: ");
            int y = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Возраст (должен быть больше 0): ");
            int age = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Вес (должен быть больше 0): ");
            long weight = Long.parseLong(scanner.nextLine());
            
            System.out.print("Тип (WATER, AIR, FIRE): ");
            String typeStr = scanner.nextLine().toUpperCase();
            DragonType type = DragonType.valueOf(typeStr);
            
            System.out.print("Характер (CUNNING, WISE, EVIL, CHAOTIC, FICKLE): ");
            String charStr = scanner.nextLine().toUpperCase();
            DragonCharacter character = DragonCharacter.valueOf(charStr);

            System.out.print("Размер головы: ");
            Float headSize = Float.parseFloat(scanner.nextLine());
            DragonHead head = new DragonHead(headSize);
            
            Coordinates coordinates = new Coordinates(x, y);
            return new Dragon(name, coordinates, LocalDateTime.now(), age, weight, type, character, head);
            
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка при создании дракона: " + e.getMessage());
            return null;
        }
    }

    private static boolean printer(SocketChannel channel) throws IOException, ClassNotFoundException {
        while (channel.isOpen()) {
            // Чтение размера данных
            ByteBuffer sizeBuffer = ByteBuffer.allocate(4);
            int totalBytesRead = 0;
            while (totalBytesRead < 4) {
                int bytesRead = channel.read(sizeBuffer);
                if (bytesRead == -1) {
                    System.out.println("Сервер закрыл соединение.");
                    channel.close();
                    Exit exit1 =new Exit();
                    exit1.execute();

                    return false;
                }
                totalBytesRead += bytesRead;
            }
            sizeBuffer.flip();
            int responseSize = sizeBuffer.getInt();

            if (responseSize < 0) {
                System.out.println("Некорректный размер данных от сервера");
                channel.close();
                return false;
            }

            // Чтение данных
            ByteBuffer readBuffer = ByteBuffer.allocate(responseSize);
            int remaining = responseSize;
            while (remaining > 0) {
                int bytesRead = channel.read(readBuffer);
                if (bytesRead == -1) {
                    System.out.println("Сервер закрыл соединение.");
                    channel.close();
                    Exit  exit1 =new Exit();
                    exit1.execute();
                    return false;
                }
                remaining -= bytesRead;
                readBuffer.rewind();
            }
            readBuffer.rewind();
            byte[] byteArray1 = new byte[readBuffer.remaining()];
            readBuffer.get(byteArray1);

            ByteArrayInputStream bais1 = new ByteArrayInputStream(byteArray1);
            try (ObjectInputStream ois1 = new ObjectInputStream(bais1)) {
                CommandResponse message = (CommandResponse) ois1.readObject();
                System.out.println("Сообщение от сервера: \n" + message.getResult());
                break;
            } catch (EOFException e) {
                System.out.println("Ошибка отправки данных");
            }
        }
        return true;


    }
    public static Serializable fromByteBuffer(ByteBuffer buffer) throws IOException, ClassNotFoundException {

        ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array());
        ObjectInputStream objectInputStream = new ObjectInputStream(bais);

        Serializable response = (Serializable) objectInputStream.readObject();

        objectInputStream.close();
        bais.close();

        return response;
    }
}