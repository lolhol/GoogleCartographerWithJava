package org.brigero.recording;

import brigero.IMUUnitree;
import brigero.MessageType;
import brigero.PointCloud;
import brigero.UnitreeLidar4Java;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UnitreeLidarRecorder extends UnitreeLidar4Java {
    private final String _K_FILE_NAME;
    private final DataOutputStream _K_FILE_WRITER;

    public UnitreeLidarRecorder(String fileName, String lidarPath) throws IOException {
        super();
        this._K_FILE_NAME = fileName;
        this._K_FILE_WRITER = new DataOutputStream(Files.newOutputStream(Paths.get(_K_FILE_NAME)));
    }

    public void writeData(MessageType message) throws IOException {
        switch (message) {
            case POINTCLOUD:
                _K_FILE_WRITER.writeUTF("POINTCLOUD");
                PointCloud cloud = (PointCloud) this.getPointCloudObject();
                _K_FILE_WRITER.writeDouble(cloud.stamp);
                _K_FILE_WRITER.writeInt(cloud.point.length);
                for (int i = 0; i < cloud.point.length; i++) {
                    _K_FILE_WRITER.writeDouble(cloud.point[i].x);
                    _K_FILE_WRITER.writeDouble(cloud.point[i].y);
                    _K_FILE_WRITER.writeDouble(cloud.point[i].z);
                }
                break;
            case IMU:
                _K_FILE_WRITER.writeUTF("IMU");
                IMUUnitree imu = this.getIMUData();
                _K_FILE_WRITER.writeDouble(imu.stamp);
                for (int i = 0; i < imu.angular_velocity.length; i++) {
                    _K_FILE_WRITER.writeDouble(imu.angular_velocity[i]);
                }
                for (int i = 0; i < imu.linear_acceleration.length; i++) {
                    _K_FILE_WRITER.writeDouble(imu.linear_acceleration[i]);
                }
                for (int i = 0; i < imu.quaternion.length; i++) {
                    _K_FILE_WRITER.writeDouble(imu.quaternion[i]);
                }
                break;
            default:
                break;
        }
    }
}
