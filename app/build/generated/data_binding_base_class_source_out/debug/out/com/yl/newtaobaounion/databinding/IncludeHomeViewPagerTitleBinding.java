// Generated by view binder compiler. Do not edit!
package com.yl.newtaobaounion.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.yl.newtaobaounion.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class IncludeHomeViewPagerTitleBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView tvHomePagerTitle;

  private IncludeHomeViewPagerTitleBinding(@NonNull LinearLayout rootView,
      @NonNull TextView tvHomePagerTitle) {
    this.rootView = rootView;
    this.tvHomePagerTitle = tvHomePagerTitle;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static IncludeHomeViewPagerTitleBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static IncludeHomeViewPagerTitleBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.include_home_view_pager_title, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static IncludeHomeViewPagerTitleBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.tv_home_pager_title;
      TextView tvHomePagerTitle = ViewBindings.findChildViewById(rootView, id);
      if (tvHomePagerTitle == null) {
        break missingId;
      }

      return new IncludeHomeViewPagerTitleBinding((LinearLayout) rootView, tvHomePagerTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
