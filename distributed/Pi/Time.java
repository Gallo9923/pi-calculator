//
// Copyright (c) ZeroC, Inc. All rights reserved.
//
//
// Ice version 3.7.7
//
// <auto-generated>
//
// Generated from file `PI.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Pi;

public class Time extends com.zeroc.Ice.Value
{
    public Time()
    {
        this.milliseconds = "";
        this.seconds = "";
        this.minutes = "";
    }

    public Time(String milliseconds, String seconds, String minutes)
    {
        this.milliseconds = milliseconds;
        this.seconds = seconds;
        this.minutes = minutes;
    }

    public String milliseconds;

    public String seconds;

    public String minutes;

    public Time clone()
    {
        return (Time)super.clone();
    }

    public static String ice_staticId()
    {
        return "::Pi::Time";
    }

    @Override
    public String ice_id()
    {
        return ice_staticId();
    }

    /** @hidden */
    public static final long serialVersionUID = 1275598602L;

    /** @hidden */
    @Override
    protected void _iceWriteImpl(com.zeroc.Ice.OutputStream ostr_)
    {
        ostr_.startSlice(ice_staticId(), -1, true);
        ostr_.writeString(milliseconds);
        ostr_.writeString(seconds);
        ostr_.writeString(minutes);
        ostr_.endSlice();
    }

    /** @hidden */
    @Override
    protected void _iceReadImpl(com.zeroc.Ice.InputStream istr_)
    {
        istr_.startSlice();
        milliseconds = istr_.readString();
        seconds = istr_.readString();
        minutes = istr_.readString();
        istr_.endSlice();
    }
}