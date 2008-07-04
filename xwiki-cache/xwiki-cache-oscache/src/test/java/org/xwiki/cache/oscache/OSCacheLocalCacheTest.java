package org.xwiki.cache.oscache;

import org.xwiki.cache.Cache;
import org.xwiki.cache.CacheFactory;
import org.xwiki.cache.config.CacheConfiguration;
import org.xwiki.cache.eviction.LRUEvictionConfiguration;
import org.xwiki.cache.tests.AbstractGenericTestCache;
import org.xwiki.component.manager.ComponentLookupException;

public class OSCacheLocalCacheTest extends AbstractGenericTestCache
{
    public OSCacheLocalCacheTest()
    {
        this("oscache/local");
    }

    protected OSCacheLocalCacheTest(String roleHint)
    {
        super(roleHint);
    }

    // ///////////////////////////////////////////////////////::
    // Tests

    public void testCreateAndDestroyCacheLRUMaxEntries() throws ComponentLookupException, Exception
    {
        CacheFactory factory = getCacheFactory();

        CacheConfiguration conf = new CacheConfiguration();
        LRUEvictionConfiguration lec = new LRUEvictionConfiguration();
        lec.setMaxEntries(1);
        conf.put(LRUEvictionConfiguration.CONFIGURATIONID, lec);

        Cache<Object> cache = factory.newCache(conf);

        assertNotNull(cache);

        cache.set(KEY, VALUE);

        assertEquals(VALUE, cache.get(KEY));

        cache.set(KEY2, VALUE2);

        assertNull(cache.get(KEY));
        assertEquals(VALUE2, cache.get(KEY2));

        cache.dispose();
    }

    public void testCreateAndDestroyCacheLRUTimeToLive() throws ComponentLookupException, Exception
    {
        CacheFactory factory = getCacheFactory();

        CacheConfiguration conf = new CacheConfiguration();
        LRUEvictionConfiguration lec = new LRUEvictionConfiguration();
        lec.setTimeToLive(1);
        conf.put(LRUEvictionConfiguration.CONFIGURATIONID, lec);

        Cache<Object> cache = factory.newCache(conf);

        assertNotNull(cache);

        cache.set(KEY, VALUE);

        assertEquals(VALUE, cache.get(KEY));

        Thread.sleep(1000);

        assertNull(cache.get(KEY));

        cache.dispose();
    }

    public void testCreateAndDestroyCacheLRUAll() throws ComponentLookupException, Exception
    {
        CacheFactory factory = getCacheFactory();

        CacheConfiguration conf = new CacheConfiguration();
        LRUEvictionConfiguration lec = new LRUEvictionConfiguration();
        lec.setMaxEntries(1);
        lec.setTimeToLive(1);
        conf.put(LRUEvictionConfiguration.CONFIGURATIONID, lec);

        Cache<Object> cache = factory.newCache(conf);

        assertNotNull(cache);

        cache.set(KEY, VALUE);

        assertEquals(VALUE, cache.get(KEY));

        cache.set(KEY2, VALUE2);

        assertNull(cache.get(KEY));
        assertEquals(VALUE2, cache.get(KEY2));

        Thread.sleep(1000);

        assertNull(cache.get(KEY));
        assertNull(cache.get(KEY2));

        cache.dispose();
    }
}
