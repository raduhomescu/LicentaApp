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
    private double width;
    private double height;
    private double depth;
    private double mass;
    private boolean dualSim;
    private double primaryCamera;
    private double frontCamera;
    private String connector;
    private String linkFlanco;
    private File localFile;
    private double price;
    private int storage;
    private String colour;
    private String link_imagine;

    public Phone() {}

    public Phone(String uId, String brand, String model, String platform, int ram, String resolution, int battery,
                 double width, double height, double depth, double mass, boolean dualSim, double primaryCamera,
                 double frontCamera, String connector, String linkFlanco, File localFile, double price, int storage,
                 String colour, String link_imagine) {
        this.uId = uId;
        this.brand = brand;
        this.model = model;
        this.platform = platform;
        this.ram = ram;
        this.resolution = resolution;
        this.battery = battery;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.mass = mass;
        this.dualSim = dualSim;
        this.primaryCamera = primaryCamera;
        this.frontCamera = frontCamera;
        this.connector = connector;
        this.linkFlanco = linkFlanco;
        this.localFile = localFile;
        this.price = price;
        this.storage = storage;
        this.colour = colour;
        this.link_imagine = link_imagine;
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

    public String getConnector() {
        return connector;
    }

    public void setConnector(String connector) {
        this.connector = connector;
    }

    public String getLinkFlanco() {
        return linkFlanco;
    }

    public void setLinkFlanco(String linkFlanco) {
        this.linkFlanco = linkFlanco;
    }

    public File getLocalFile() { return localFile; }

    public void setLocalFile(File localFile) { this.localFile = localFile; }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public String getLink_imagine() {
        return link_imagine;
    }

    public void setLink_imagine(String link_imagine) {
        this.link_imagine = link_imagine;
    }

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
                ", width=" + width +
                ", height=" + height +
                ", depth=" + depth +
                ", mass=" + mass +
                ", dualSim=" + dualSim +
                ", primaryCamera=" + primaryCamera +
                ", frontCamera=" + frontCamera +
                ", connector='" + connector + '\'' +
                ", linkFlanco='" + linkFlanco + '\'' +
                ", localFile=" + localFile +
                ", price=" + price +
                ", storage=" + storage +
                ", colour='" + colour + '\'' +
                ", link_imagine='" + link_imagine + '\'' +
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
        width = in.readDouble();
        height = in.readDouble();
        depth = in.readDouble();
        mass = in.readDouble();
        dualSim = in.readByte() != 0;
        primaryCamera = in.readDouble();
        frontCamera = in.readDouble();
        connector = in.readString();
        linkFlanco = in.readString();
        price = in.readDouble();
        storage = in.readInt();
        colour = in.readString();
        link_imagine = in.readString();

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
        dest.writeDouble(width);
        dest.writeDouble(height);
        dest.writeDouble(depth);
        dest.writeDouble(mass);
        dest.writeByte((byte) (dualSim ? 1 : 0));
        dest.writeDouble(primaryCamera);
        dest.writeDouble(frontCamera);
        dest.writeString(connector);
        dest.writeString(linkFlanco);
        dest.writeDouble(price);
        dest.writeInt(storage);
        dest.writeString(colour);
        dest.writeString(link_imagine);
    }
}
