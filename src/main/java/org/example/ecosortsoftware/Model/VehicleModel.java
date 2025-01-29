package org.example.ecosortsoftware.Model;

import org.example.ecosortsoftware.dto.Tm.VehicleTm;
import org.example.ecosortsoftware.dto.VehicleDto;
import org.example.ecosortsoftware.DAO.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleModel {
    public String getNextVehicleId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("select vehicle_id from vehicle order by vehicle_id desc limit 1;");

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
        ResultSet resultSet = SQLUtil.execute("select * from vehicle where muni_id=?", municipalId);
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
        return SQLUtil.execute("insert into vehicle values(?,?,?,?,?)",
                vehicleDto.getVehicleId(),vehicleDto.getEmployeeId(),vehicleDto.getLicensePlate(),vehicleDto.getVehicleType(),vehicleDto.getMunicipalId());
    }

    public boolean updateVehicle(VehicleDto vehicleDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("Update vehicle SET employee_id=?, license_plate=?, type=?, muni_id=? WHERE vehicle_id=?",
                vehicleDto.getEmployeeId(),vehicleDto.getLicensePlate(),vehicleDto.getVehicleType(),vehicleDto.getMunicipalId(),vehicleDto.getVehicleId());
    }

    public boolean DeleteVehicle(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM vehicle WHERE vehicle_id=?",id);
    }
}
