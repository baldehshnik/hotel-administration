package com.vd.university.data.source;

import com.vd.university.data.entity.ClientEntity;
import com.vd.university.data.entity.OrderEntity;
import com.vd.university.data.entity.RoomEntity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReportDataSource {

    private final File file;

    public ReportDataSource() throws IOException {
        this.file = new File("report/report.txt");
        if (!file.exists()) {
            file.createNewFile();
        } else {
            new FileWriter(file, false).close();
        }

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
        String timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        bufferedWriter.write("REPORT DATE - " + timeFormat);
        bufferedWriter.newLine();

        bufferedWriter.close();
    }

    public void writeRoomsInfo(List<RoomEntity> rooms) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));

        bufferedWriter.newLine();
        bufferedWriter.write("\t\t\tROOMS");
        bufferedWriter.newLine();
        for (RoomEntity room : rooms) {
            bufferedWriter.write("ID " + room.getId() + ";" +
                    " NUMBER " + room.getNumber() + ";" +
                    " BEDS " + room.getBedsCount() + ";" +
                    " TYPE " + room.getType() + ";"
            );
            bufferedWriter.newLine();
        }

        bufferedWriter.newLine();
        bufferedWriter.close();
    }

    public void writeClientsInfo(List<ClientEntity> clients) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));

        bufferedWriter.newLine();
        bufferedWriter.write("\t\t\tCLIENTS");
        bufferedWriter.newLine();
        for (ClientEntity client : clients) {
            bufferedWriter.write("ID " + client.getId() + ";" +
                    " FIRSTNAME " + client.getFirstname() + ";" +
                    " LASTNAME " + client.getLastname() + ";" +
                    " PATRONYMIC " + client.getPatronymic() + ";" +
                    " PASSPORT " + client.getPassport() + ";"
            );
            bufferedWriter.newLine();
        }

        bufferedWriter.newLine();
        bufferedWriter.close();
    }

    public void writeOrdersInfo(List<OrderEntity> orders) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        bufferedWriter.newLine();
        bufferedWriter.write("\t\t\tORDERS");
        bufferedWriter.newLine();
        for (OrderEntity order : orders) {
            long time = order.getTime();
            Date date = new Date(time);

            bufferedWriter.write("ID " + order.getId() + ";" +
                    " CLIENT PASSPORT " + order.getClient().getPassport() + ";" +
                    " ROOM NUMBER " + order.getRoom().getNumber() + ";" +
                    " EXPIRATION DATE " + dateFormat.format(date)
            );
            bufferedWriter.newLine();
        }

        bufferedWriter.newLine();
        bufferedWriter.close();
    }
}