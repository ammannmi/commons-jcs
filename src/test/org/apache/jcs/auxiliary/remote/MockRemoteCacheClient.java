package org.apache.jcs.auxiliary.remote;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jcs.auxiliary.AbstractAuxiliaryCache;
import org.apache.jcs.auxiliary.AuxiliaryCacheAttributes;
import org.apache.jcs.auxiliary.remote.behavior.IRemoteCacheClient;
import org.apache.jcs.auxiliary.remote.behavior.IRemoteCacheListener;
import org.apache.jcs.auxiliary.remote.behavior.IRemoteCacheService;
import org.apache.jcs.engine.CacheConstants;
import org.apache.jcs.engine.behavior.ICacheElement;
import org.apache.jcs.engine.stats.behavior.IStats;

/**
 * Used for testing the no wait.
 * <p>
 * @author Aaron Smuts
 */
public class MockRemoteCacheClient<K extends Serializable, V extends Serializable>
    extends AbstractAuxiliaryCache<K, V>
    implements IRemoteCacheClient<K, V>
{
    /** For serialization. Don't change. */
    private static final long serialVersionUID = 1L;

    /** log instance */
    private final static Log log = LogFactory.getLog( MockRemoteCacheClient.class );

    /** List of ICacheElement<K, V> objects passed into update. */
    public List<ICacheElement<K, V>> updateList = new LinkedList<ICacheElement<K,V>>();

    /** List of key objects passed into remove. */
    public List<K> removeList = new LinkedList<K>();

    /** status to return. */
    public int status = CacheConstants.STATUS_ALIVE;

    /** Can setup values to return from get. values must be ICacheElement<K, V> */
    public Map<K, ICacheElement<K, V>> getSetupMap = new HashMap<K, ICacheElement<K,V>>();

    /** Can setup values to return from get. values must be Map<K, ICacheElement<K, V>> */
    public Map<Set<K>, Map<K, ICacheElement<K, V>>> getMultipleSetupMap =
        new HashMap<Set<K>, Map<K,ICacheElement<K,V>>>();

    /** The last service passed to fixCache */
    public IRemoteCacheService<K, V> fixed;

    /** Attributes. */
    public RemoteCacheAttributes attributes = new RemoteCacheAttributes();

    /**
     * Stores the last argument as fixed.
     * <p>
     * (non-Javadoc)
     * @see org.apache.jcs.auxiliary.remote.behavior.IRemoteCacheClient#fixCache(org.apache.jcs.auxiliary.remote.behavior.IRemoteCacheService)
     */
    public void fixCache( IRemoteCacheService<K, V> remote )
    {
        fixed = remote;
    }

    /**
     * @return long
     */
    public long getListenerId()
    {
        return 0;
    }

    /**
     * @return null
     */
    public IRemoteCacheListener<K, V> getListener()
    {
        return null;
    }

    /**
     * Adds the argument to the updatedList.
     * <p>
     * (non-Javadoc)
     * @see org.apache.jcs.auxiliary.AuxiliaryCache#update(org.apache.jcs.engine.behavior.ICacheElement)
     */
    public void update( ICacheElement<K, V> ce )
    {
        updateList.add( ce );
    }

    /**
     * Looks in the getSetupMap for a value.
     * <p>
     * (non-Javadoc)
     * @see org.apache.jcs.auxiliary.AuxiliaryCache#get(java.io.Serializable)
     */
    public ICacheElement<K, V> get( K key )
    {
        log.info( "get [" + key + "]" );
        return getSetupMap.get( key );
    }

    /**
     * Gets multiple items from the cache based on the given set of keys.
     * <p>
     * @param keys
     * @return a map of K key to ICacheElement<K, V> element, or an empty map if there is no
     *         data in cache for any of these keys
     */
    public Map<K, ICacheElement<K, V>> getMultiple(Set<K> keys)
    {
        log.info( "get [" + keys + "]" );
        return getMultipleSetupMap.get( keys );
    }

    /**
     * Adds the key to the remove list.
     * <p>
     * (non-Javadoc)
     * @see org.apache.jcs.auxiliary.AuxiliaryCache#remove(java.io.Serializable)
     */
    public boolean remove( K key )
    {
        removeList.add( key );
        return false;
    }

    /**
     * Removes all cached items from the cache.
     */
    public void removeAll()
    {
        // do nothing
    }

    /**
     * Prepares for shutdown.
     */
    public void dispose()
    {
        // do nothing
    }

    /**
     * Returns the current cache size in number of elements.
     * <p>
     * @return number of elements
     */
    public int getSize()
    {
        return 0;
    }

    /**
     * Returns the status setup variable. (non-Javadoc)
     * @see org.apache.jcs.auxiliary.AuxiliaryCache#getStatus()
     */
    public int getStatus()
    {
        return status;
    }

    /**
     * Returns the cache name.
     * <p>
     * @return usually the region name.
     */
    public String getCacheName()
    {
        return null;
    }

    /**
     * @param group
     * @return null
     */
    public Set<K> getGroupKeys( String group )
    {
        return null;
    }

    /**
     * @return null
     */
    public IStats getStatistics()
    {
        return null;
    }

    /**
     * Returns the setup attributes. By default they are not null.
     * <p>
     * (non-Javadoc)
     * @see org.apache.jcs.auxiliary.AuxiliaryCache#getAuxiliaryCacheAttributes()
     */
    public AuxiliaryCacheAttributes getAuxiliaryCacheAttributes()
    {
        return attributes;
    }

    /**
     * Returns the cache stats.
     * <p>
     * @return String of important historical information.
     */
    public String getStats()
    {
        return null;
    }

    /** @return 0 */
    public int getCacheType()
    {
        return 0;
    }

    /**
     * @param pattern
     * @return Map
     * @throws IOException
     */
    public Map<K, ICacheElement<K, V>> getMatching(String pattern)
        throws IOException
    {
        return new HashMap<K, ICacheElement<K,V>>();
    }

    /**
     * Nothing important
     * <p>
     * @return null
     */
    @Override
    public String getEventLoggingExtraInfo()
    {
        return null;
    }
}