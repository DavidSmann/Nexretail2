package com.nexvis.nexretail.utils;

import android.content.pm.PackageManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.nexvis.nexretail.utils.SinglePermission;
import java.util.ArrayList;

/*
    this is a sample how to use this class in activity or fragment

      PermissionUtil.PermissionRequestObject mStoragePermissionRequest = PermissionUtil.with(this).request(WRITE_EXTERNAL_STORAGE).onAllGranted(new Func() {
            @Override protected void call() {
                doOnPermissionGranted("Storage");
            }
        }).onAnyDenied(new Func() {
            @Override protected void call() {
                doOnPermissionDenied("Storage");
            }
        }).ask(REQUEST_CODE_STORAGE);
 */

public class PermissionUtil {
    public static PermissionObject with(AppCompatActivity activity) {
        return new PermissionObject(activity);
    }

    public static PermissionObject with(Fragment fragment) {
        return new PermissionObject(fragment);
    }

    public static class PermissionObject {

        private AppCompatActivity mActivity;
        private Fragment mFragment;

        PermissionObject(AppCompatActivity activity) {
            mActivity = activity;
        }

        PermissionObject(Fragment fragment) {
            mFragment = fragment;
        }

        public PermissionRequestObject request(String permissionName) {
            if (mActivity != null) {
                return new PermissionRequestObject(mActivity, new String[]{permissionName});
            } else {
                return new PermissionRequestObject(mFragment, new String[]{permissionName});
            }
        }

        public PermissionRequestObject request(String... permissionNames) {
            if (mActivity != null) {
                return new PermissionRequestObject(mActivity, permissionNames);
            } else {
                return new PermissionRequestObject(mFragment, permissionNames);
            }
        }

        public boolean checkPermissionExist(String permissionName) {

            int permissionCheck;
            if (mActivity != null) {
                permissionCheck = ContextCompat.checkSelfPermission(mActivity, permissionName);
            } else {
                permissionCheck = ContextCompat.checkSelfPermission(mFragment.getContext(), permissionName);
            }

            return permissionCheck == PackageManager.PERMISSION_GRANTED;
        }
    }

    static public class PermissionRequestObject {

        private AppCompatActivity mActivity;
        private Func mDenyFunc;
        private Fragment mFragment;
        private Func mGrantFunc;
        private String[] mPermissionNames;
        private ArrayList<SinglePermission> mPermissionsWeDontHave;
        private int mRequestCode;

        public PermissionRequestObject(AppCompatActivity activity, String[] permissionNames) {
            mActivity = activity;
            mPermissionNames = permissionNames;
        }

        public PermissionRequestObject(Fragment fragment, String[] permissionNames) {
            mFragment = fragment;
            mPermissionNames = permissionNames;
        }

        public PermissionRequestObject askPermission(int reqCode) {

            mRequestCode = reqCode;
            int length = mPermissionNames.length;
            mPermissionsWeDontHave = new ArrayList<>(length);
            for (String mPermissionName : mPermissionNames) {
                mPermissionsWeDontHave.add(new SinglePermission(mPermissionName));
            }

            if (needToAsk()) {
                if (mActivity != null) {
                    ActivityCompat.requestPermissions(mActivity, mPermissionNames, reqCode);
                } else {
                    mFragment.requestPermissions(mPermissionNames, reqCode);
                }
            } else {

                if (mGrantFunc != null) {
                    mGrantFunc.call();
                }
            }
            return this;
        }

        private boolean needToAsk() {

            ArrayList<SinglePermission> neededPermissions = new ArrayList<>(mPermissionsWeDontHave);

            for (int i = 0; i < mPermissionsWeDontHave.size(); i++) {

                SinglePermission perm = mPermissionsWeDontHave.get(i);

                int checkRes;

                if (mActivity != null) {
                    checkRes = ContextCompat.checkSelfPermission(mActivity, perm.getPermissionName());
                } else {
                    checkRes = ContextCompat.checkSelfPermission(mFragment.getContext(), perm.getPermissionName());
                }
                if (checkRes == PackageManager.PERMISSION_GRANTED) {
                    neededPermissions.remove(perm);
                } else {
                    boolean shouldShowRequestPermissionRationale;
                    if (mActivity != null) {
                        shouldShowRequestPermissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(mActivity, perm.getPermissionName());
                    } else {
                        shouldShowRequestPermissionRationale = mFragment.shouldShowRequestPermissionRationale(perm.getPermissionName());
                    }
                    if (shouldShowRequestPermissionRationale) {
                        perm.setRationalNeeded(true);
                    }
                }
            }

            mPermissionsWeDontHave = neededPermissions;
            mPermissionNames = new String[mPermissionsWeDontHave.size()];

            for (int i = 0; i < mPermissionsWeDontHave.size(); i++) {
                mPermissionNames[i] = mPermissionsWeDontHave.get(i).getPermissionName();
            }

            return mPermissionsWeDontHave.size() != 0;
        }

        public PermissionRequestObject onAllGranted(Func grantFunc) {
            mGrantFunc = grantFunc;
            return this;
        }

        public PermissionRequestObject onAnyDenied(Func denyFunc) {
            mDenyFunc = denyFunc;
            return this;
        }

        //Call this function in onRequestPermissionsResult in Activity or Fragment
        public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
            if (mRequestCode == requestCode) {

                for (int per : grantResults) {

                    if (per == PackageManager.PERMISSION_DENIED)
                        mDenyFunc.call();
                    else {
                        mGrantFunc.call();
                    }
                }
            }
        }
    }

    public abstract static class Func {
        protected abstract void call();
    }
}
