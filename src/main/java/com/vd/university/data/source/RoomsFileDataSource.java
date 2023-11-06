package com.vd.university.data.source;

import com.google.gson.Gson;
import com.vd.university.data.entity.RoomEntity;
import com.vd.university.di.annotation.SingletonScope;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SingletonScope
public class RoomsFileDataSource {

    public List<RoomEntity> readRooms() throws IOException {
        File file = new File("rooms.txt");
        if (!file.exists()) return new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(file));
        Gson gson = new Gson();

        List<RoomEntity> rooms = new ArrayList<>();

        String line;
        while ((line = reader.readLine()) != null) {
            RoomEntity room = gson.fromJson(line, RoomEntity.class);
            rooms.add(room);
        }

        reader.close();
        file.delete();
        return rooms;
    }
}