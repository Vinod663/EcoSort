package org.example.ecosortsoftware.Model;

import org.example.ecosortsoftware.DTO.Tm.EmployeeTm;
import org.example.ecosortsoftware.DTO.Tm.VehicleTm;
import org.example.ecosortsoftware.DTO.VehicleDto;
import org.example.ecosortsoftware.Utill.CrudUtill;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleModel {
    public String getNextVehicleId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtill.execute("select vehicle_id from vehicle order by vehicle_id desc limit 1;");

        if (resultSet.next()) {
            String lastVeId = resultSet.getString(1);
            String subString = lastVeId.substring(1);
            int i = Integer.parseInt(subString);
            int newIndex = i + 1;

            return String.format("V0%03d", newIndex);
        }
        return "V0001";
    }

    public ArrayList<VehicleTm> getAll(String municipalId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtill.execute("select * from vehicle where muni_id=?", municipalId);
        ArrayList<VehicleTm> vehTms = new ArrayList<>();

        while (resultSet.next()) {
            VehicleTm vehTm = new VehicleTm();

            vehTm.setVehicleId(resultSet.getString(1));
            vehTm.setEmployeeId(resultSet.getString(2));
            vehTm.setLicensePlate(resultSet.getString(3));
            vehTm.setVehicleType(resultSet.getString(4));
            vehTm.setMunicipalId(resultSet.getString(5));

            vehTms.add(vehTm);
        }
        return vehTms;
    }

    public boolean saveVehicle(VehicleDto vehicleDto) throws SQLException, ClassNotFoundException {
        return CrudUtill.execute("insert into vehicle values(?,?,?,?,?)",
                vehicleDto.getVehicleId(),vehicleDto.getEmployeeId(),vehicleDto.getLicensePlate(),vehicleDto.getVehicleType(),vehicleDto.getMunicipalId());
    }

    public boolean updateVehicle(VehicleDto vehicleDto) throws SQLException, ClassNotFoundException {
        return CrudUtill.execute("Update vehicle SET employee_id=?, license_plate=?, type=?, muni_id=? WHERE vehicle_id=?",
                vehicleDto.getEmployeeId(),vehicleDto.getLicensePlate(),vehicleDto.getVehicleType(),vehicleDto.getMunicipalId(),vehicleDto.getVehicleId());
    }

    public boolean DeleteVehicle(String id) throws SQLException, ClassNotFoundException {
        return CrudUtill.execute("DELETE FROM vehicle WHERE vehicle_id=?",id);
    }
}
