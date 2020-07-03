package interfaces;

import exceptionhandlers.InvalidDataException;
import exceptionhandlers.MissingDataException;

public interface IClassroom {

    public void setRoomNumber(String p_roomNumber) throws InvalidDataException, MissingDataException;
    public void setTypeOfRoom(String p_roomType) throws InvalidDataException, MissingDataException;
    public void setCapacity(String p_capacity) throws InvalidDataException, MissingDataException;

    public String getRoomNumber();
    public String getTypeOfRoom();
    public int getCapacity();

}
