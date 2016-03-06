package com.example.ddude.cityclean;

/**
 * Created by ddude on 05-Mar-16.
 */
public class PastIssue {

    private int _id;
    private String _pastissue;
    private String _location;
    private String _discription;

    public PastIssue(String _discription, String _location, String _pastissue) {
        this._discription = _discription;
        this._location = _location;
        this._pastissue = _pastissue;
    }

    public void set_discription(String _discription) {
        this._discription = _discription;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_location(String _location) {
        this._location = _location;
    }

    public void set_pastissue(String _pastissue) {
        this._pastissue = _pastissue;
    }


    public String get_discription() {
        return _discription;
    }

    public int get_id() {
        return _id;
    }

    public String get_location() {
        return _location;
    }

    public String get_pastissue() {
        return _pastissue;
    }
}
