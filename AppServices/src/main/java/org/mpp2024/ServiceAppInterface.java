package org.mpp2024;

import org.mpp2024.Utility.Validation.ValidException;

public interface ServiceAppInterface {
    void Login(Account account, AppObserverInterface observer) throws AppException;
    void Register(String usernanme,String password) throws AppException;
    void Logout(Account account, AppObserverInterface observer) throws AppException;
    void MakeReservation(Account account, String name, String phone, Long tickets, Trip trip) throws AppException;
    Iterable<Trip>  Get_All_Trips() throws AppException;
    Iterable<Trip> Get_All_Trip_By_Destination_From_To(String destination,int startDate,int finishDate) throws AppException;

    Account MakeAccount(String username ,String password) throws ValidException, AppException;
}
