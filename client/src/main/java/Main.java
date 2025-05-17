import managers.CollectionManager;
import managers.Controller;
import managers.commands.Exit;
import response.CommandResponse;

import java.io.*;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Main {
    public static int PORT = 2024;

    public static void main(String[] args) throws IOException, ClassNotFoundException, ConnectException {

        try {

            SocketChannel channel = SocketChannel.open();
            channel.connect(new InetSocketAddress("localhost", 6042));
            channel.configureBlocking(false);
            Controller controller = new Controller();
            System.out.println("Успешное подключение к серверу");
            while (true) {

                controller.run();
                if (CollectionManager.request.getName()!="skip") {

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
            System.out.println("Не удалось подключиться");
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
                    exit1.execute("exit");

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
                    exit1.execute("exit");
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