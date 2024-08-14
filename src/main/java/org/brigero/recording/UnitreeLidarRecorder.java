package org.brigero.recording;

import brigero.UnitreeLidar4Java;

import java.io.FileWriter;
import java.io.IOException;

public class UnitreeLidarRecorder extends UnitreeLidar4Java {
    private final String _K_FILE_NAME;
    private final FileWriter _K_FILE_WRITER;

    public UnitreeLidarRecorder(String fileName) throws IOException {
        super();
        this._K_FILE_NAME = fileName;
        this._K_FILE_WRITER = new FileWriter(fileName);
    }

    public void writeData(MessageType message) throws IOException {
        switch (message) {
            case POINTCLOUD:
                this._K_FILE_WRITER.append("POINTCLOUD ");
                PointCloud cloud = (PointCloud) this.getPointCloudObject();
                this._K_FILE_WRITER.append((char) cloud.stamp);
                for (int i = 0; i < cloud.point.length; i++) {
                    this._K_FILE_WRITER.append(" ");
                    this._K_FILE_WRITER.append((char) cloud.point[i].x);
                    this._K_FILE_WRITER.append(" ");
                    this._K_FILE_WRITER.append((char) cloud.point[i].y);
                    this._K_FILE_WRITER.append(" ");
                    this._K_FILE_WRITER.append((char) cloud.point[i].z);

                    if (i == cloud.point.length - 1) {
                        this._K_FILE_WRITER.append(" ");
                    }
                }
                break;
            case IMU:
                this._K_FILE_WRITER.append("IMU ");
                IMUUnitree imu = this.getIMUData();
                this._K_FILE_WRITER.append((char) imu.stamp);
                for (int i = 0; i < imu.angular_velocity.length; i++) {
                    this._K_FILE_WRITER.append(" ");
                    this._K_FILE_WRITER.append((char) imu.angular_velocity[i]);
                }

                for (int i = 0; i < imu.linear_acceleration.length; i++) {
                    this._K_FILE_WRITER.append(" ");
                    this._K_FILE_WRITER.append((char) imu.linear_acceleration[i]);
                }

                for (int i = 0; i < imu.quaternion.length; i++) {
                    this._K_FILE_WRITER.append(" ");
                    this._K_FILE_WRITER.append((char) imu.quaternion[i]);
                }
                this._K_FILE_WRITER.append(" ");
                break;
            default:
                break;
        }
    }
}
