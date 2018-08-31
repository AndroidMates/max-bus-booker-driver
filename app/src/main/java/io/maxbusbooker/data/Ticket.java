package io.maxbusbooker.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Project : max-travel-aid
 * Created by Dennis Bilson on Wed at 7:08 PM.
 * Package name : io.travelaid.data
 *
 * Ticket data model
 */
public class Ticket implements Parcelable {
	private Passenger passenger;
	private Bus bus;
	//top: TimeOfPurchase
	private long timeOfPurchase;
	//Key is the database reference to the ticket. This will enable us find the specific ticket
	// in the database reference
	private String key;
	private int seatNumber;
	private String ticketNumber;
	
	public Ticket() {
	}
	
	public Ticket(Passenger passenger, Bus bus, String key, int seatNumber, String ticketNumber) {
		this.passenger = passenger;
		this.bus = bus;
		this.timeOfPurchase = System.currentTimeMillis();
		this.key = key;
		this.seatNumber = seatNumber;
		this.ticketNumber = ticketNumber;
	}
	
	protected Ticket(Parcel in) {
		passenger = in.readParcelable(Passenger.class.getClassLoader());
		bus = in.readParcelable(Bus.class.getClassLoader());
		timeOfPurchase = in.readLong();
		key = in.readString();
		seatNumber = in.readInt();
		ticketNumber = in.readString();
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(passenger, flags);
		dest.writeParcelable(bus, flags);
		dest.writeLong(timeOfPurchase);
		dest.writeString(key);
		dest.writeInt(seatNumber);
		dest.writeString(ticketNumber);
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	public static final Creator<Ticket> CREATOR = new Creator<Ticket>() {
		@Override
		public Ticket createFromParcel(Parcel in) {
			return new Ticket(in);
		}
		
		@Override
		public Ticket[] newArray(int size) {
			return new Ticket[size];
		}
	};
	
	public Passenger getPassenger() {
		return passenger;
	}
	
	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}
	
	public Bus getBus() {
		return bus;
	}
	
	public void setBus(Bus bus) {
		this.bus = bus;
	}
	
	public long getTimeOfPurchase() {
		return timeOfPurchase;
	}
	
	public void setTimeOfPurchase(long timeOfPurchase) {
		this.timeOfPurchase = timeOfPurchase;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public int getSeatNumber() {
		return seatNumber;
	}
	
	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}
	
	public String getTicketNumber() {
		return ticketNumber;
	}
	
	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
	
	@Override
	public String toString() {
		return "Ticket{" +
				"passenger=" + passenger +
				", bus=" + bus +
				", timeOfPurchase=" + timeOfPurchase +
				", key='" + key + '\'' +
				", seatNumber=" + seatNumber +
				", ticketNumber='" + ticketNumber + '\'' +
				'}';
	}
}
