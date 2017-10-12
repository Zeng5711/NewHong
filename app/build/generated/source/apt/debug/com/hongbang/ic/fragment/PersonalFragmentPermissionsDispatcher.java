// This file was generated by PermissionsDispatcher. Do not modify!
package com.hongbang.ic.fragment;

import java.lang.Override;
import java.lang.String;
import java.lang.ref.WeakReference;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.PermissionUtils;
import permissions.dispatcher.v13.PermissionUtilsV13;

final class PersonalFragmentPermissionsDispatcher {
  private static final int REQUEST_STARTALBUM = 3;

  private static final String[] PERMISSION_STARTALBUM = new String[] {"android.permission.WRITE_EXTERNAL_STORAGE"};

  private static final int REQUEST_STARTCAPTURE = 4;

  private static final String[] PERMISSION_STARTCAPTURE = new String[] {"android.permission.CAMERA","android.permission.WRITE_EXTERNAL_STORAGE"};

  private PersonalFragmentPermissionsDispatcher() {
  }

  static void startAlbumWithCheck(PersonalFragment target) {
    if (PermissionUtils.hasSelfPermissions(target.getActivity(), PERMISSION_STARTALBUM)) {
      target.startAlbum();
    } else {
      if (PermissionUtilsV13.getInstance().shouldShowRequestPermissionRationale(target, PERMISSION_STARTALBUM)) {
        target.showNoSdcardPermissions(new StartAlbumPermissionRequest(target));
      } else {
        PermissionUtilsV13.getInstance().requestPermissions(target, PERMISSION_STARTALBUM, REQUEST_STARTALBUM);
      }
    }
  }

  static void startCaptureWithCheck(PersonalFragment target) {
    if (PermissionUtils.hasSelfPermissions(target.getActivity(), PERMISSION_STARTCAPTURE)) {
      target.startCapture();
    } else {
      if (PermissionUtilsV13.getInstance().shouldShowRequestPermissionRationale(target, PERMISSION_STARTCAPTURE)) {
        target.showCameraRationale(new StartCapturePermissionRequest(target));
      } else {
        PermissionUtilsV13.getInstance().requestPermissions(target, PERMISSION_STARTCAPTURE, REQUEST_STARTCAPTURE);
      }
    }
  }

  static void onRequestPermissionsResult(PersonalFragment target, int requestCode, int[] grantResults) {
    switch (requestCode) {
      case REQUEST_STARTALBUM:
      if (PermissionUtils.getTargetSdkVersion(target.getActivity()) < 23 && !PermissionUtils.hasSelfPermissions(target.getActivity(), PERMISSION_STARTALBUM)) {
        target.showDeniedForSdcard();
        return;
      }
      if (PermissionUtils.verifyPermissions(grantResults)) {
        target.startAlbum();
      } else {
        if (!PermissionUtilsV13.getInstance().shouldShowRequestPermissionRationale(target, PERMISSION_STARTALBUM)) {
          target.showDeniedForSdcard();
        } else {
          target.showDeniedForSdcard();
        }
      }
      break;
      case REQUEST_STARTCAPTURE:
      if (PermissionUtils.getTargetSdkVersion(target.getActivity()) < 23 && !PermissionUtils.hasSelfPermissions(target.getActivity(), PERMISSION_STARTCAPTURE)) {
        target.showDeniedForCamera();
        return;
      }
      if (PermissionUtils.verifyPermissions(grantResults)) {
        target.startCapture();
      } else {
        if (!PermissionUtilsV13.getInstance().shouldShowRequestPermissionRationale(target, PERMISSION_STARTCAPTURE)) {
          target.showDeniedForCamera();
        } else {
          target.showDeniedForCamera();
        }
      }
      break;
      default:
      break;
    }
  }

  private static final class StartAlbumPermissionRequest implements PermissionRequest {
    private final WeakReference<PersonalFragment> weakTarget;

    private StartAlbumPermissionRequest(PersonalFragment target) {
      this.weakTarget = new WeakReference<>(target);
    }

    @Override
    public void proceed() {
      PersonalFragment target = weakTarget.get();
      if (target == null) return;
      PermissionUtilsV13.getInstance().requestPermissions(target, PERMISSION_STARTALBUM, REQUEST_STARTALBUM);
    }

    @Override
    public void cancel() {
      PersonalFragment target = weakTarget.get();
      if (target == null) return;
      target.showDeniedForSdcard();
    }
  }

  private static final class StartCapturePermissionRequest implements PermissionRequest {
    private final WeakReference<PersonalFragment> weakTarget;

    private StartCapturePermissionRequest(PersonalFragment target) {
      this.weakTarget = new WeakReference<>(target);
    }

    @Override
    public void proceed() {
      PersonalFragment target = weakTarget.get();
      if (target == null) return;
      PermissionUtilsV13.getInstance().requestPermissions(target, PERMISSION_STARTCAPTURE, REQUEST_STARTCAPTURE);
    }

    @Override
    public void cancel() {
      PersonalFragment target = weakTarget.get();
      if (target == null) return;
      target.showDeniedForCamera();
    }
  }
}
