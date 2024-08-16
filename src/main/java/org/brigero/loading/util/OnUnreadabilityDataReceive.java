package org.brigero.loading.util;

public interface OnUnreadabilityDataReceive {
    void onPointCloudData(float[][] cloud, double timeStamp);
    void onIMUData(float[] angular_velocity, float[] linear_acceleration, float[] quaternion, double timeStamp);
}
