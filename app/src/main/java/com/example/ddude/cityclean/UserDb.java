package com.example.ddude.cityclean;

/**
 * Created by ddude on 05-Mar-16.
 */
public class UserDb {

    private int _id;
    private String _name;
    private String _phoneno;

    public UserDb(String _name, String _phoneno) {
        this._name = _name;
        this._phoneno = _phoneno;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public void set_phoneno(String _phoneno) {
        this._phoneno = _phoneno;
    }

    public int get_id() {
        return _id;
    }

    public String get_name() {
        return _name;
    }

    public String get_phoneno() {
        return _phoneno;
    }
}
