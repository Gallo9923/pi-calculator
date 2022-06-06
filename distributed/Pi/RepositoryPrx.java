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

public interface RepositoryPrx extends com.zeroc.Ice.ObjectPrx
{
    default Job createJob(Job job)
    {
        return createJob(job, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default Job createJob(Job job, java.util.Map<String, String> context)
    {
        return _iceI_createJobAsync(job, context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<Job> createJobAsync(Job job)
    {
        return _iceI_createJobAsync(job, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Job> createJobAsync(Job job, java.util.Map<String, String> context)
    {
        return _iceI_createJobAsync(job, context, false);
    }

    /**
     * @hidden
     * @param iceP_job -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<Job> _iceI_createJobAsync(Job iceP_job, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Job> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "createJob", null, sync, null);
        f.invoke(true, context, null, ostr -> {
                     ostr.writeValue(iceP_job);
                     ostr.writePendingValues();
                 }, istr -> {
                     final com.zeroc.IceInternal.Holder<Job> ret = new com.zeroc.IceInternal.Holder<>();
                     istr.readValue(v -> ret.value = v, Job.class);
                     istr.readPendingValues();
                     return ret.value;
                 });
        return f;
    }

    default boolean verifyPendingTasks()
    {
        return verifyPendingTasks(com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default boolean verifyPendingTasks(java.util.Map<String, String> context)
    {
        return _iceI_verifyPendingTasksAsync(context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<java.lang.Boolean> verifyPendingTasksAsync()
    {
        return _iceI_verifyPendingTasksAsync(com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<java.lang.Boolean> verifyPendingTasksAsync(java.util.Map<String, String> context)
    {
        return _iceI_verifyPendingTasksAsync(context, false);
    }

    /**
     * @hidden
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<java.lang.Boolean> _iceI_verifyPendingTasksAsync(java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<java.lang.Boolean> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "verifyPendingTasks", null, sync, null);
        f.invoke(true, context, null, null, istr -> {
                     boolean ret;
                     ret = istr.readBool();
                     return ret;
                 });
        return f;
    }

    default void addIntermediateResult(TaskResult taskResult)
    {
        addIntermediateResult(taskResult, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default void addIntermediateResult(TaskResult taskResult, java.util.Map<String, String> context)
    {
        _iceI_addIntermediateResultAsync(taskResult, context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<Void> addIntermediateResultAsync(TaskResult taskResult)
    {
        return _iceI_addIntermediateResultAsync(taskResult, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Void> addIntermediateResultAsync(TaskResult taskResult, java.util.Map<String, String> context)
    {
        return _iceI_addIntermediateResultAsync(taskResult, context, false);
    }

    /**
     * @hidden
     * @param iceP_taskResult -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<Void> _iceI_addIntermediateResultAsync(TaskResult iceP_taskResult, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Void> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "addIntermediateResult", null, sync, null);
        f.invoke(false, context, null, ostr -> {
                     ostr.writeValue(iceP_taskResult);
                     ostr.writePendingValues();
                 }, null);
        return f;
    }

    default Job getJob(String jobId)
    {
        return getJob(jobId, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default Job getJob(String jobId, java.util.Map<String, String> context)
    {
        return _iceI_getJobAsync(jobId, context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<Job> getJobAsync(String jobId)
    {
        return _iceI_getJobAsync(jobId, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Job> getJobAsync(String jobId, java.util.Map<String, String> context)
    {
        return _iceI_getJobAsync(jobId, context, false);
    }

    /**
     * @hidden
     * @param iceP_jobId -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<Job> _iceI_getJobAsync(String iceP_jobId, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Job> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "getJob", null, sync, null);
        f.invoke(true, context, null, ostr -> {
                     ostr.writeString(iceP_jobId);
                 }, istr -> {
                     final com.zeroc.IceInternal.Holder<Job> ret = new com.zeroc.IceInternal.Holder<>();
                     istr.readValue(v -> ret.value = v, Job.class);
                     istr.readPendingValues();
                     return ret.value;
                 });
        return f;
    }

    default void setJobResult(JobResult job)
    {
        setJobResult(job, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default void setJobResult(JobResult job, java.util.Map<String, String> context)
    {
        _iceI_setJobResultAsync(job, context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<Void> setJobResultAsync(JobResult job)
    {
        return _iceI_setJobResultAsync(job, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Void> setJobResultAsync(JobResult job, java.util.Map<String, String> context)
    {
        return _iceI_setJobResultAsync(job, context, false);
    }

    /**
     * @hidden
     * @param iceP_job -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<Void> _iceI_setJobResultAsync(JobResult iceP_job, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Void> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "setJobResult", null, sync, null);
        f.invoke(false, context, null, ostr -> {
                     ostr.writeValue(iceP_job);
                     ostr.writePendingValues();
                 }, null);
        return f;
    }

    /**
     * Contacts the remote server to verify that the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static RepositoryPrx checkedCast(com.zeroc.Ice.ObjectPrx obj)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, ice_staticId(), RepositoryPrx.class, _RepositoryPrxI.class);
    }

    /**
     * Contacts the remote server to verify that the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param context The Context map to send with the invocation.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static RepositoryPrx checkedCast(com.zeroc.Ice.ObjectPrx obj, java.util.Map<String, String> context)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, context, ice_staticId(), RepositoryPrx.class, _RepositoryPrxI.class);
    }

    /**
     * Contacts the remote server to verify that a facet of the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static RepositoryPrx checkedCast(com.zeroc.Ice.ObjectPrx obj, String facet)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, facet, ice_staticId(), RepositoryPrx.class, _RepositoryPrxI.class);
    }

    /**
     * Contacts the remote server to verify that a facet of the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @param context The Context map to send with the invocation.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static RepositoryPrx checkedCast(com.zeroc.Ice.ObjectPrx obj, String facet, java.util.Map<String, String> context)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, facet, context, ice_staticId(), RepositoryPrx.class, _RepositoryPrxI.class);
    }

    /**
     * Downcasts the given proxy to this type without contacting the remote server.
     * @param obj The untyped proxy.
     * @return A proxy for this type.
     **/
    static RepositoryPrx uncheckedCast(com.zeroc.Ice.ObjectPrx obj)
    {
        return com.zeroc.Ice.ObjectPrx._uncheckedCast(obj, RepositoryPrx.class, _RepositoryPrxI.class);
    }

    /**
     * Downcasts the given proxy to this type without contacting the remote server.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @return A proxy for this type.
     **/
    static RepositoryPrx uncheckedCast(com.zeroc.Ice.ObjectPrx obj, String facet)
    {
        return com.zeroc.Ice.ObjectPrx._uncheckedCast(obj, facet, RepositoryPrx.class, _RepositoryPrxI.class);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the per-proxy context.
     * @param newContext The context for the new proxy.
     * @return A proxy with the specified per-proxy context.
     **/
    @Override
    default RepositoryPrx ice_context(java.util.Map<String, String> newContext)
    {
        return (RepositoryPrx)_ice_context(newContext);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the adapter ID.
     * @param newAdapterId The adapter ID for the new proxy.
     * @return A proxy with the specified adapter ID.
     **/
    @Override
    default RepositoryPrx ice_adapterId(String newAdapterId)
    {
        return (RepositoryPrx)_ice_adapterId(newAdapterId);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the endpoints.
     * @param newEndpoints The endpoints for the new proxy.
     * @return A proxy with the specified endpoints.
     **/
    @Override
    default RepositoryPrx ice_endpoints(com.zeroc.Ice.Endpoint[] newEndpoints)
    {
        return (RepositoryPrx)_ice_endpoints(newEndpoints);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the locator cache timeout.
     * @param newTimeout The new locator cache timeout (in seconds).
     * @return A proxy with the specified locator cache timeout.
     **/
    @Override
    default RepositoryPrx ice_locatorCacheTimeout(int newTimeout)
    {
        return (RepositoryPrx)_ice_locatorCacheTimeout(newTimeout);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the invocation timeout.
     * @param newTimeout The new invocation timeout (in seconds).
     * @return A proxy with the specified invocation timeout.
     **/
    @Override
    default RepositoryPrx ice_invocationTimeout(int newTimeout)
    {
        return (RepositoryPrx)_ice_invocationTimeout(newTimeout);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for connection caching.
     * @param newCache <code>true</code> if the new proxy should cache connections; <code>false</code> otherwise.
     * @return A proxy with the specified caching policy.
     **/
    @Override
    default RepositoryPrx ice_connectionCached(boolean newCache)
    {
        return (RepositoryPrx)_ice_connectionCached(newCache);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the endpoint selection policy.
     * @param newType The new endpoint selection policy.
     * @return A proxy with the specified endpoint selection policy.
     **/
    @Override
    default RepositoryPrx ice_endpointSelection(com.zeroc.Ice.EndpointSelectionType newType)
    {
        return (RepositoryPrx)_ice_endpointSelection(newType);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for how it selects endpoints.
     * @param b If <code>b</code> is <code>true</code>, only endpoints that use a secure transport are
     * used by the new proxy. If <code>b</code> is false, the returned proxy uses both secure and
     * insecure endpoints.
     * @return A proxy with the specified selection policy.
     **/
    @Override
    default RepositoryPrx ice_secure(boolean b)
    {
        return (RepositoryPrx)_ice_secure(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the encoding used to marshal parameters.
     * @param e The encoding version to use to marshal request parameters.
     * @return A proxy with the specified encoding version.
     **/
    @Override
    default RepositoryPrx ice_encodingVersion(com.zeroc.Ice.EncodingVersion e)
    {
        return (RepositoryPrx)_ice_encodingVersion(e);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its endpoint selection policy.
     * @param b If <code>b</code> is <code>true</code>, the new proxy will use secure endpoints for invocations
     * and only use insecure endpoints if an invocation cannot be made via secure endpoints. If <code>b</code> is
     * <code>false</code>, the proxy prefers insecure endpoints to secure ones.
     * @return A proxy with the specified selection policy.
     **/
    @Override
    default RepositoryPrx ice_preferSecure(boolean b)
    {
        return (RepositoryPrx)_ice_preferSecure(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the router.
     * @param router The router for the new proxy.
     * @return A proxy with the specified router.
     **/
    @Override
    default RepositoryPrx ice_router(com.zeroc.Ice.RouterPrx router)
    {
        return (RepositoryPrx)_ice_router(router);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the locator.
     * @param locator The locator for the new proxy.
     * @return A proxy with the specified locator.
     **/
    @Override
    default RepositoryPrx ice_locator(com.zeroc.Ice.LocatorPrx locator)
    {
        return (RepositoryPrx)_ice_locator(locator);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for collocation optimization.
     * @param b <code>true</code> if the new proxy enables collocation optimization; <code>false</code> otherwise.
     * @return A proxy with the specified collocation optimization.
     **/
    @Override
    default RepositoryPrx ice_collocationOptimized(boolean b)
    {
        return (RepositoryPrx)_ice_collocationOptimized(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses twoway invocations.
     * @return A proxy that uses twoway invocations.
     **/
    @Override
    default RepositoryPrx ice_twoway()
    {
        return (RepositoryPrx)_ice_twoway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses oneway invocations.
     * @return A proxy that uses oneway invocations.
     **/
    @Override
    default RepositoryPrx ice_oneway()
    {
        return (RepositoryPrx)_ice_oneway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses batch oneway invocations.
     * @return A proxy that uses batch oneway invocations.
     **/
    @Override
    default RepositoryPrx ice_batchOneway()
    {
        return (RepositoryPrx)_ice_batchOneway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses datagram invocations.
     * @return A proxy that uses datagram invocations.
     **/
    @Override
    default RepositoryPrx ice_datagram()
    {
        return (RepositoryPrx)_ice_datagram();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses batch datagram invocations.
     * @return A proxy that uses batch datagram invocations.
     **/
    @Override
    default RepositoryPrx ice_batchDatagram()
    {
        return (RepositoryPrx)_ice_batchDatagram();
    }

    /**
     * Returns a proxy that is identical to this proxy, except for compression.
     * @param co <code>true</code> enables compression for the new proxy; <code>false</code> disables compression.
     * @return A proxy with the specified compression setting.
     **/
    @Override
    default RepositoryPrx ice_compress(boolean co)
    {
        return (RepositoryPrx)_ice_compress(co);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its connection timeout setting.
     * @param t The connection timeout for the proxy in milliseconds.
     * @return A proxy with the specified timeout.
     **/
    @Override
    default RepositoryPrx ice_timeout(int t)
    {
        return (RepositoryPrx)_ice_timeout(t);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its connection ID.
     * @param connectionId The connection ID for the new proxy. An empty string removes the connection ID.
     * @return A proxy with the specified connection ID.
     **/
    @Override
    default RepositoryPrx ice_connectionId(String connectionId)
    {
        return (RepositoryPrx)_ice_connectionId(connectionId);
    }

    /**
     * Returns a proxy that is identical to this proxy, except it's a fixed proxy bound
     * the given connection.@param connection The fixed proxy connection.
     * @return A fixed proxy bound to the given connection.
     **/
    @Override
    default RepositoryPrx ice_fixed(com.zeroc.Ice.Connection connection)
    {
        return (RepositoryPrx)_ice_fixed(connection);
    }

    static String ice_staticId()
    {
        return "::Pi::Repository";
    }
}
