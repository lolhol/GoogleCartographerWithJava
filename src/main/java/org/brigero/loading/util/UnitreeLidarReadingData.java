package org.brigero.loading.util;

import java.io.*;
import java.nio.file.Files;

public class UnitreeLidarReadingData extends Thread {
    private final DataInputStream _K_FILE_READER;
    private final OnUnreadabilityDataReceive _K_CALLBACK;
    private final long _K_TIME_STEP;
    private boolean _isOnline = true;

    public UnitreeLidarReadingData(File filePath, OnUnreadabilityDataReceive callback, long timestep)
            throws IOException {
        this._K_FILE_READER = new DataInputStream(Files.newInputStream(filePath.toPath()));
        this._K_CALLBACK = callback;
        this._K_TIME_STEP = timestep;
    }

    public void run() {
        try {
            while (this._isOnline) {
                try {
                    String firstName = _K_FILE_READER.readUTF().trim();
                    switch (firstName) {
                        case "POINTCLOUD":
                            handlePointCloud();
                            break;
                        case "IMU":
                            handleIMU();
                            break;
                        default:
                            System.out.println("Unknown data type: " + firstName);
                            break;
                    }
                } catch (EOFException e) {
                    break;
                }

                Thread.sleep(_K_TIME_STEP);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                _K_FILE_READER.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handlePointCloud() throws IOException {
        double stamp = _K_FILE_READER.readDouble();
        int len = _K_FILE_READER.readInt();
        double[][] points = new double[len][3];
        for (int i = 0; i < len; i++) {
            points[i] = new double[] {
                    _K_FILE_READER.readDouble(),
                    _K_FILE_READER.readDouble(),
                    _K_FILE_READER.readDouble()
            };
        }

        _K_CALLBACK.onPointCloudData(points, stamp);
    }

    private void handleIMU() throws IOException {
        double stamp = _K_FILE_READER.readDouble();
        double[] angular_velocity = new double[3];
        for (int i = 0; i < 3; i++) {
            angular_velocity[i] = _K_FILE_READER.readDouble();
        }
        double[] linear_acceleration = new double[3];
        for (int i = 0; i < 3; i++) {
            linear_acceleration[i] = _K_FILE_READER.readDouble();
        }
        double[] quaternion = new double[4];
        for (int i = 0; i < 4; i++) {
            quaternion[i] = _K_FILE_READER.readDouble();
        }

        _K_CALLBACK.onIMUData(angular_velocity, linear_acceleration, quaternion, stamp);
    }

    public void setState(boolean newState) {
        this._isOnline = newState;
    }
}
