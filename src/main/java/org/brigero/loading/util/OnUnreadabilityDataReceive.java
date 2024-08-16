package org.brigero.loading.util;

public interface OnUnreadabilityDataReceive {
    void onPointCloudData(double[][] cloud, double timeStamp);
    void onIMUData(double[] angular_velocity, double[] linear_acceleration, double[] quaternion, double timeStamp);
}
