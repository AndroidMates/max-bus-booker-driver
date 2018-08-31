package io.maxbusbooker.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Project : max-travel-aid
 * Created by Dennis Bilson on Wed at 6:45 PM.
 * Package name : io.travelaid.data
 */
public class Seat implements Parcelable {
	private String number;
	private boolean isBooked = false;
	private Passenger passenger;
	
	public Seat() {
	}
	
	public Seat(String number, boolean isBooked, Passenger passenger) {
		this.number = number;
		this.isBooked = isBooked;
		this.passenger = passenger;
	}
	
	protected Seat(Parcel in) {
		number = in.readString();
		isBooked = in.readByte() != 0;
		passenger = in.readParcelable(Passenger.class.getClassLoader());
	}
	
	public static final Creator<Seat> CREATOR = new Creator<Seat>() {
		@Override
		public Seat createFromParcel(Parcel in) {
			return new Seat(in);
		}
		
		@Override
		public Seat[] newArray(int size) {
			return new Seat[size];
		}
	};
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public boolean isBooked() {
		return isBooked;
	}
	
	public void setBooked(boolean booked) {
		isBooked = booked;
	}
	
	public Passenger getPassenger() {
		return passenger;
	}
	
	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel parcel, int i) {
		
		parcel.writeString(number);
		parcel.writeByte((byte) (isBooked ? 1 : 0));
		parcel.writeParcelable(passenger, i);
	}
}
