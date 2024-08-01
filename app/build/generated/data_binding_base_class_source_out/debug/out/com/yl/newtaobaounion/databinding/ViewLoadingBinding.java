// Generated by view binder compiler. Do not edit!
package com.yl.newtaobaounion.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.yl.newtaobaounion.R;
import com.yl.newtaobaounion.ui.custom.MyLoadingView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ViewLoadingBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final MyLoadingView myLoadingView;

  @NonNull
  public final TextView textView3;

  private ViewLoadingBinding(@NonNull ConstraintLayout rootView,
      @NonNull MyLoadingView myLoadingView, @NonNull TextView textView3) {
    this.rootView = rootView;
    this.myLoadingView = myLoadingView;
    this.textView3 = textView3;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ViewLoadingBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ViewLoadingBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.view_loading, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ViewLoadingBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.my_loading_view;
      MyLoadingView myLoadingView = ViewBindings.findChildViewById(rootView, id);
      if (myLoadingView == null) {
        break missingId;
      }

      id = R.id.textView3;
      TextView textView3 = ViewBindings.findChildViewById(rootView, id);
      if (textView3 == null) {
        break missingId;
      }

      return new ViewLoadingBinding((ConstraintLayout) rootView, myLoadingView, textView3);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
