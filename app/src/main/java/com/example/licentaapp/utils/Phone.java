package com.example.licentaapp.utils;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.File;

public class Phone implements Parcelable {
    private String uId;
    private String brand;
    private String model;
    private String platform;
    private int ram;
    private String resolution;
    private int battery;
    private int storage;
    private String colour;
    private double width;
    private double height;
    private double depth;
    private double mass;
    private boolean dualSim;
    private double primaryCamera;
    private double frontCamera;
    private int year;
    private double price;
    private String connector;
    private String linkAltex;
    private String linkEmag;
    private String linkFlanco;
    private File localFile;

    public Phone() {}

    public Phone(String uId, String brand, String model, String platform, int ram, String resolution, int battery, int storage,
                 String colour, double width, double height, double depth, double mass, boolean dualSim, double primaryCamera,
                 double frontCamera, int year, double price, String connector, String linkAltex, String linkEmag,String linkFlanco, File localFile) {
        this.uId = uId;
        this.brand = brand;
        this.model = model;
        this.platform = platform;
        this.ram = ram;
        this.resolution = resolution;
        this.battery = battery;
        this.storage = storage;
        this.colour = colour;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.mass = mass;
        this.dualSim = dualSim;
        this.primaryCamera = primaryCamera;
        this.frontCamera = frontCamera;
        this.year = year;
        this.price = price;
        this.connector = connector;
        this.linkAltex = linkAltex;
        this.linkEmag = linkEmag;
        this.linkFlanco = linkFlanco;
        this.localFile = localFile;
    }

    public static final Creator<Phone> CREATOR = new Creator<Phone>() {
        @Override
        public Phone createFromParcel(Parcel in) {
            return new Phone(in);
        }

        @Override
        public Phone[] newArray(int size) {
            return new Phone[size];
        }
    };

    public String getuId() { return uId; }

    public void setuId(String uId) { this.uId = uId; }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getDepth() {
        return depth;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public boolean isDualSim() {
        return dualSim;
    }

    public void setDualSim(boolean dualSim) {
        this.dualSim = dualSim;
    }

    public double getPrimaryCamera() {
        return primaryCamera;
    }

    public void setPrimaryCamera(double primaryCamera) {
        this.primaryCamera = primaryCamera;
    }

    public double getFrontCamera() {
        return frontCamera;
    }

    public void setFrontCamera(double frontCamera) {
        this.frontCamera = frontCamera;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getConnector() {
        return connector;
    }

    public void setConnector(String connector) {
        this.connector = connector;
    }

    public String getLinkAltex() {
        return linkAltex;
    }

    public void setLinkAltex(String linkAltex) {
        this.linkAltex = linkAltex;
    }

    public String getLinkEmag() {
        return linkEmag;
    }

    public void setLinkEmag(String linkEmag) {
        this.linkEmag = linkEmag;
    }

    public String getLinkFlanco() {
        return linkFlanco;
    }

    public void setLinkFlanco(String linkFlanco) {
        this.linkFlanco = linkFlanco;
    }

    public File getLocalFile() { return localFile; }

    public void setLocalFile(File localFile) { this.localFile = localFile; }

    @Override
    public String toString() {
        return "Phone{" +
                "uId='" + uId + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", platform='" + platform + '\'' +
                ", ram=" + ram +
                ", resolution='" + resolution + '\'' +
                ", battery=" + battery +
                ", storage=" + storage +
                ", colour='" + colour + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", depth=" + depth +
                ", mass=" + mass +
                ", dualSim=" + dualSim +
                ", primaryCamera=" + primaryCamera +
                ", frontCamera=" + frontCamera +
                ", year=" + year +
                ", price=" + price +
                ", connector='" + connector + '\'' +
                '}';
    }

    public Phone(Parcel in) {
        uId = in.readString();
        brand = in.readString();
        model = in.readString();
        platform = in.readString();
        ram = in.readInt();
        resolution = in.readString();
        battery = in.readInt();
        storage = in.readInt();
        colour = in.readString();
        width = in.readDouble();
        height = in.readDouble();
        depth = in.readDouble();
        mass = in.readDouble();
        dualSim = in.readByte() != 0;
        primaryCamera = in.readDouble();
        frontCamera = in.readDouble();
        year = in.readInt();
        price = in.readDouble();
        connector = in.readString();
        linkAltex = in.readString();
        linkEmag = in.readString();
        linkFlanco = in.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(uId);
        dest.writeString(brand);
        dest.writeString(model);
        dest.writeString(platform);
        dest.writeInt(ram);
        dest.writeString(resolution);
        dest.writeInt(battery);
        dest.writeInt(storage);
        dest.writeString(colour);
        dest.writeDouble(width);
        dest.writeDouble(height);
        dest.writeDouble(depth);
        dest.writeDouble(mass);
        dest.writeByte((byte) (dualSim ? 1 : 0));
        dest.writeDouble(primaryCamera);
        dest.writeDouble(frontCamera);
        dest.writeInt(year);
        dest.writeDouble(price);
        dest.writeString(connector);
        dest.writeString(linkAltex);
        dest.writeString(linkEmag);
        dest.writeString(linkFlanco);
    }
}
