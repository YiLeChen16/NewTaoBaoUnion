// Generated by view binder compiler. Do not edit!
package com.yl.newtaobaounion.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.yl.newtaobaounion.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemHomeViewPageContentBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ImageView ivGoodsImg;

  @NonNull
  public final RelativeLayout layoutHead;

  @NonNull
  public final TextView tvCheap;

  @NonNull
  public final TextView tvEndTime;

  @NonNull
  public final TextView tvNormalPrice;

  @NonNull
  public final TextView tvPrice;

  @NonNull
  public final TextView tvPrice1;

  @NonNull
  public final TextView tvTag;

  @NonNull
  public final TextView tvTitle;

  private ItemHomeViewPageContentBinding(@NonNull RelativeLayout rootView,
      @NonNull ImageView ivGoodsImg, @NonNull RelativeLayout layoutHead, @NonNull TextView tvCheap,
      @NonNull TextView tvEndTime, @NonNull TextView tvNormalPrice, @NonNull TextView tvPrice,
      @NonNull TextView tvPrice1, @NonNull TextView tvTag, @NonNull TextView tvTitle) {
    this.rootView = rootView;
    this.ivGoodsImg = ivGoodsImg;
    this.layoutHead = layoutHead;
    this.tvCheap = tvCheap;
    this.tvEndTime = tvEndTime;
    this.tvNormalPrice = tvNormalPrice;
    this.tvPrice = tvPrice;
    this.tvPrice1 = tvPrice1;
    this.tvTag = tvTag;
    this.tvTitle = tvTitle;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemHomeViewPageContentBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemHomeViewPageContentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_home_view_page_content, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemHomeViewPageContentBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.iv_goods_img;
      ImageView ivGoodsImg = ViewBindings.findChildViewById(rootView, id);
      if (ivGoodsImg == null) {
        break missingId;
      }

      id = R.id.layout_head;
      RelativeLayout layoutHead = ViewBindings.findChildViewById(rootView, id);
      if (layoutHead == null) {
        break missingId;
      }

      id = R.id.tv_cheap;
      TextView tvCheap = ViewBindings.findChildViewById(rootView, id);
      if (tvCheap == null) {
        break missingId;
      }

      id = R.id.tv_end_time;
      TextView tvEndTime = ViewBindings.findChildViewById(rootView, id);
      if (tvEndTime == null) {
        break missingId;
      }

      id = R.id.tv_normal_price;
      TextView tvNormalPrice = ViewBindings.findChildViewById(rootView, id);
      if (tvNormalPrice == null) {
        break missingId;
      }

      id = R.id.tv_price;
      TextView tvPrice = ViewBindings.findChildViewById(rootView, id);
      if (tvPrice == null) {
        break missingId;
      }

      id = R.id.tv_price1;
      TextView tvPrice1 = ViewBindings.findChildViewById(rootView, id);
      if (tvPrice1 == null) {
        break missingId;
      }

      id = R.id.tv_tag;
      TextView tvTag = ViewBindings.findChildViewById(rootView, id);
      if (tvTag == null) {
        break missingId;
      }

      id = R.id.tv_title;
      TextView tvTitle = ViewBindings.findChildViewById(rootView, id);
      if (tvTitle == null) {
        break missingId;
      }

      return new ItemHomeViewPageContentBinding((RelativeLayout) rootView, ivGoodsImg, layoutHead,
          tvCheap, tvEndTime, tvNormalPrice, tvPrice, tvPrice1, tvTag, tvTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
