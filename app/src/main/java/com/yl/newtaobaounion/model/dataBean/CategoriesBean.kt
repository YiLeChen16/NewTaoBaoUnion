package com.yl.newtaobaounion.model.dataBean

import android.os.Parcel
import android.os.Parcelable

data class CategoriesBean(
    val code: Int,
    val `data`: List<CategoriesData>,
    val msg: String,
    val success: Boolean
)

data class CategoriesData(
    val clickCount: Int,
    val color: String,
    val createTime: String,
    val enable: String,
    val icon: String,
    val id: String,
    val ord: Int,
    val parentId: String,
    val subCategoryList: List<SubCategory>,
    val updateTime: String,
    val word: String
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.createTypedArrayList(SubCategory)!!,
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(clickCount)
        parcel.writeString(color)
        parcel.writeString(createTime)
        parcel.writeString(enable)
        parcel.writeString(icon)
        parcel.writeString(id)
        parcel.writeInt(ord)
        parcel.writeString(parentId)
        parcel.writeTypedList(subCategoryList)
        parcel.writeString(updateTime)
        parcel.writeString(word)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CategoriesData> {
        override fun createFromParcel(parcel: Parcel): CategoriesData {
            return CategoriesData(parcel)
        }

        override fun newArray(size: Int): Array<CategoriesData?> {
            return arrayOfNulls(size)
        }
    }
}


data class SubCategory(
    val clickCount: Int,
    val color: String,
    val createTime: String,
    val enable: String,
    val icon: String,
    val id: String,
    val ord: Int,
    val parentId: String,
    val updateTime: String,
    val word: String
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(clickCount)
        parcel.writeString(color)
        parcel.writeString(createTime)
        parcel.writeString(enable)
        parcel.writeString(icon)
        parcel.writeString(id)
        parcel.writeInt(ord)
        parcel.writeString(parentId)
        parcel.writeString(updateTime)
        parcel.writeString(word)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SubCategory> {
        override fun createFromParcel(parcel: Parcel): SubCategory {
            return SubCategory(parcel)
        }

        override fun newArray(size: Int): Array<SubCategory?> {
            return arrayOfNulls(size)
        }
    }
}

