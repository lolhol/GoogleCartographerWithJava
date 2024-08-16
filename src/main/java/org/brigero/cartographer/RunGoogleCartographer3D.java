package org.brigero.cartographer;

import brigero.cartographer4java.Cartographer3D;
import brigero.cartographer4java.Sensor;

import org.brigero.loading.util.OnUnreadabilityDataReceive;
import org.brigero.loading.util.UnitreeLidarReadingData;
import org.brigero.render.RenderFileWriter;

import java.io.File;
import java.io.IOException;

public class RunGoogleCartographer3D {

    public static void main(String[] args) throws IOException, InterruptedException {
        Cartographer3D cartographer3D = new Cartographer3D();
        cartographer3D.init("google-cartographer-config/3d", "trajectory_builder.lua",
                10.0, new Sensor[] { new Sensor("imu0", 0, 0, 0) }, new Sensor[] {},
                new Sensor[] { new Sensor("range0", 0, 0, 0) });

        System.out.println("Sleeping!");
        Thread.sleep(1500);

        UnitreeLidarReadingData reader = new UnitreeLidarReadingData(new File(args[0]),
                new OnUnreadabilityDataReceive() {
                    @Override
                    public void onPointCloudData(float[][] cloud, double timeStamp) {
                        System.out.println("CLOUD!");
                        float[] x = new float[cloud.length];
                        float[] y = new float[cloud.length];
                        float[] z = new float[cloud.length];
                        float[] intensity = new float[cloud.length];
                        for (int i = 0; i < cloud.length; i++) {
                            x[i] = cloud[i][0];
                            y[i] = cloud[i][1];
                            z[i] = cloud[i][2];
                            intensity[i] = cloud[i][3];
                        }
                        cartographer3D.addLidarData((long) timeStamp, "range0", x, y, z, intensity);
                    }

                    @Override
                    public void onIMUData(float[] angular_velocity, float[] linear_acceleration, float[] quaternion,
                            double timeStamp) {
                        System.out.println("IMU!");
                        cartographer3D.addIMUData((long) timeStamp, "imu0", linear_acceleration, angular_velocity,
                                quaternion);
                    }
                }, Long.parseLong(args[1]));

        RenderFileWriter writer = new RenderFileWriter(new File(args[2]), Long.parseLong(args[3]));

        // writer.start();
        reader.start();

        /*
         * while (true) {
         * writer.addPointCloudData(cartographer3D.paintMap());
         * 
         * try {
         * Thread.sleep(1000);
         * } catch (InterruptedException e) {
         * e.printStackTrace();
         * }
         * }
         */
    }
}
