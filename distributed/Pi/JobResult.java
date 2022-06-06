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

public class JobResult extends com.zeroc.Ice.Value
{
    public JobResult()
    {
        this.jobId = "";
        this.finishDate = "";
        this.pi = "";
    }

    public JobResult(String jobId, String finishDate, double repNumbers, String pi)
    {
        this.jobId = jobId;
        this.finishDate = finishDate;
        this.repNumbers = repNumbers;
        this.pi = pi;
    }

    public String jobId;

    public String finishDate;

    public double repNumbers;

    public String pi;

    public JobResult clone()
    {
        return (JobResult)super.clone();
    }

    public static String ice_staticId()
    {
        return "::Pi::JobResult";
    }

    @Override
    public String ice_id()
    {
        return ice_staticId();
    }

    /** @hidden */
    public static final long serialVersionUID = -1984965691L;

    /** @hidden */
    @Override
    protected void _iceWriteImpl(com.zeroc.Ice.OutputStream ostr_)
    {
        ostr_.startSlice(ice_staticId(), -1, true);
        ostr_.writeString(jobId);
        ostr_.writeString(finishDate);
        ostr_.writeDouble(repNumbers);
        ostr_.writeString(pi);
        ostr_.endSlice();
    }

    /** @hidden */
    @Override
    protected void _iceReadImpl(com.zeroc.Ice.InputStream istr_)
    {
        istr_.startSlice();
        jobId = istr_.readString();
        finishDate = istr_.readString();
        repNumbers = istr_.readDouble();
        pi = istr_.readString();
        istr_.endSlice();
    }
}