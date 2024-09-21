// Generated by view binder compiler. Do not edit!
package com.yl.newtaobaounion.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.yl.newtaobaounion.R;
import com.yl.newtaobaounion.ui.custom.BannerView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentHomeBaseBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final BannerView bannerView;

  @NonNull
  public final FrameLayout baseLayout;

  @NonNull
  public final EditText edSearchBox;

  @NonNull
  public final ImageView ivScan;

  @NonNull
  public final RelativeLayout relativeLayout;

  @NonNull
  public final TextView tvAppName;

  private FragmentHomeBaseBinding(@NonNull ConstraintLayout rootView,
      @NonNull BannerView bannerView, @NonNull FrameLayout baseLayout,
      @NonNull EditText edSearchBox, @NonNull ImageView ivScan,
      @NonNull RelativeLayout relativeLayout, @NonNull TextView tvAppName) {
    this.rootView = rootView;
    this.bannerView = bannerView;
    this.baseLayout = baseLayout;
    this.edSearchBox = edSearchBox;
    this.ivScan = ivScan;
    this.relativeLayout = relativeLayout;
    this.tvAppName = tvAppName;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentHomeBaseBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentHomeBaseBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_home_base, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentHomeBaseBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.banner_view;
      BannerView bannerView = ViewBindings.findChildViewById(rootView, id);
      if (bannerView == null) {
        break missingId;
      }

      id = R.id.base_layout;
      FrameLayout baseLayout = ViewBindings.findChildViewById(rootView, id);
      if (baseLayout == null) {
        break missingId;
      }

      id = R.id.ed_search_box;
      EditText edSearchBox = ViewBindings.findChildViewById(rootView, id);
      if (edSearchBox == null) {
        break missingId;
      }

      id = R.id.iv_scan;
      ImageView ivScan = ViewBindings.findChildViewById(rootView, id);
      if (ivScan == null) {
        break missingId;
      }

      id = R.id.relativeLayout;
      RelativeLayout relativeLayout = ViewBindings.findChildViewById(rootView, id);
      if (relativeLayout == null) {
        break missingId;
      }

      id = R.id.tv_app_name;
      TextView tvAppName = ViewBindings.findChildViewById(rootView, id);
      if (tvAppName == null) {
        break missingId;
      }

      return new FragmentHomeBaseBinding((ConstraintLayout) rootView, bannerView, baseLayout,
          edSearchBox, ivScan, relativeLayout, tvAppName);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
