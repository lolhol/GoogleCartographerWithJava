package org.brigero.render;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class RenderFileWriter extends Thread {
    private final DataOutputStream _K_FILE_WRITER;
    private final long _K_TIME_STEP;

    private HashSet<double[]> _pointCloudData; // <x, y, z, i>
    private double[] _position;
    private boolean _isOnline = true;

    public RenderFileWriter(File file, long timestep) throws IOException {
        this._K_FILE_WRITER = new DataOutputStream(Files.newOutputStream(file.toPath()));
        this._K_TIME_STEP = timestep;
    }

    public void addPointCloudData(double[][] data) {
        _pointCloudData.addAll(Arrays.asList(data));
    }

    public void addPointCloudData(float[] data) {
        for (int i = 0; i < data.length; i += 4) {
            _pointCloudData.add(new double[]{data[i], data[i + 1], data[i + 2], data[i + 3]});
        }
    }

    public void addPosition(double[] position) {
        _position = position;
    }

    @Override
    public void run() {
        while (_isOnline) {
            try {
                _K_FILE_WRITER.writeInt(_pointCloudData.size());
                for (double[] point : _pointCloudData) {
                    _K_FILE_WRITER.writeDouble(point[0]);
                    _K_FILE_WRITER.writeDouble(point[1]);
                    _K_FILE_WRITER.writeDouble(point[2]);
                    _K_FILE_WRITER.writeDouble(point[3]);
                }

                _K_FILE_WRITER.writeInt(_position.length);
                for (double v : _position) {
                    _K_FILE_WRITER.writeDouble(v);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                Thread.sleep(_K_TIME_STEP);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
