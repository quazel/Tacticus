package com.bramble.kickback.fragments;

import android.app.Fragment;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bramble.kickback.R;
import com.bramble.kickback.handlers.PhotoHandler;

public class CameraFragment extends Fragment{

    private Camera camera;
    private int cameraId = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_camera, container, false);

        cameraId = findFrontFacingCamera();
        if(cameraId == -1) {
            cameraId = findRearFacingCamera();
        }
        if(cameraId > 0) {
            camera = Camera.open(cameraId);
        }

        return view;
    }

    public void capturePicture(){
        camera.takePicture(null, null,
                new PhotoHandler(getActivity().getApplicationContext()));
    }

    private int findFrontFacingCamera() {
        int cameraId = -1;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }

    private int findRearFacingCamera() {
        int cameraId = -1;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }

    @Override
    public void onPause() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        if(camera == null) {
            cameraId = findFrontFacingCamera();
            if(cameraId == -1) {
                cameraId = findRearFacingCamera();
            }
            if(cameraId > 0) {
                camera = Camera.open(cameraId);
            }
        }
        super.onResume();
    }
}
