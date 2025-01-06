package com.example.mitfahrerzentrale.app.services;

import com.example.mitfahrerzentrale.data.dtos.BookingDTO;
import com.example.mitfahrerzentrale.data.dtos.RideDTO;
import com.example.mitfahrerzentrale.data.dtos.UserDTO;
import com.example.mitfahrerzentrale.data.entities.Booking;
import com.example.mitfahrerzentrale.data.entities.Ride;
import com.example.mitfahrerzentrale.data.entities.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class DtoWrapper {

    public static UserDTO userToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setRole(user.getRole());
        userDTO.setPasswordHash(user.getPasswordHash());
        userDTO.setIsActive(user.getIsActive());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        return userDTO;
    }

    public static RideDTO rideToDTO(Ride ride) {
        RideDTO rideDTO = new RideDTO();
        rideDTO.setId(ride.getId());
        rideDTO.setRadius(ride.getRadius());
        rideDTO.setStartTime(ride.getStartTime());
        rideDTO.setPassengerCount(ride.getPassengerCount());
        rideDTO.setPrice(ride.getPrice());
        rideDTO.setStartLocation(ride.getStartLocation());
        rideDTO.setStartLonCoordinates(ride.getStartLonCoordinates());
        rideDTO.setStartLatCoordinates(ride.getStartLatCoordinates());
        rideDTO.setDestinationLocation(ride.getDestinationLocation());
        rideDTO.setDestLatCoordinates(ride.getDestLatCoordinates());
        rideDTO.setDestLonCoordinates(ride.getDestLonCoordinates());
        rideDTO.setDriverId(ride.getDriver().getId());
        rideDTO.setArrivalTime(ride.getArrivalTime());
        rideDTO.setIsActive(ride.getIsActive());
        rideDTO.setCreatedAt(ride.getCreatedAt());
        rideDTO.setMaxPassengers(ride.getMaxPassengers());
        rideDTO.setStatus(ride.getStatus());
        return rideDTO;
    }

    public static BookingDTO bookingToDTO(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setRide(rideToDTO(booking.getRide()));
        bookingDTO.setPassengerId(booking.getPassenger().getId());
        bookingDTO.setPassengerCount(booking.getPassengerCount());
        bookingDTO.setBookingStatus(booking.getBookingStatus());
        bookingDTO.setIsActive(booking.getIsActive());
        bookingDTO.setBookedAt(booking.getBookedAt());
        return bookingDTO;
    }

    public static List<UserDTO> usersToDTOs(List<User> users) {
        return users.stream()
                .map(DtoWrapper::userToDTO)
                .collect(Collectors.toList());
    }

    public static List<RideDTO> ridesToDTOs(List<Ride> rides) {
        return rides.stream()
                .map(DtoWrapper::rideToDTO)
                .collect(Collectors.toList());
    }

    public static List<BookingDTO> bookingsToDTOs(List<Booking> bookings) {
        return bookings.stream()
                .map(DtoWrapper::bookingToDTO)
                .collect(Collectors.toList());
    }
}
