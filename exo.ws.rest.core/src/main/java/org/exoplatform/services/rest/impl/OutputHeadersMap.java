/*
 * Copyright (C) 2009 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.exoplatform.services.rest.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

/**
 * Case insensitive {@link MultivaluedMap}.
 * 
 * @author <a href="mailto:andrew00x@gmail.com">Andrey Parfonov</a>
 * @version $Id: $
 */
//TODO this implementation is not efficient, probably can be better extend
//java.util.AbstractMap
public final class OutputHeadersMap extends HashMap<String, List<Object>> implements MultivaluedMap<String, Object>
{

   /**
    * Generated by Eclipse.
    */
   private static final long serialVersionUID = 7932373258809348405L;

   /**
    * Default constructor.
    */
   public OutputHeadersMap()
   {
   }

   // MultivaluedMap

   /**
    * {@inheritDoc}
    */
   public void add(String key, Object value)
   {
      if (value == null)
         return;

      List<Object> l = getList(key);
      l.add(value);
   }

   /**
    * {@inheritDoc}
    */
   public Object getFirst(String key)
   {
      List<Object> l = get(key);
      return l != null && l.size() > 0 ? l.get(0) : null;
   }

   /**
    * {@inheritDoc}
    */
   public void putSingle(String key, Object value)
   {
      if (value == null)
         return;

      List<Object> l = getList(key);
      l.clear();
      l.add(value);
   }

   /**
    * Get List with specified key. If List does not exist new one be created.
    * 
    * @param key a key.
    * @return List&lt;Object&gt;
    */
   private List<Object> getList(String key)
   {
      List<Object> l = get(key);
      if (l == null)
      {
         l = new ArrayList<Object>();
         put(key, l);
      }

      return l;
   }

   // HashMap

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean containsKey(Object key)
   {
      if (key != null)
         return super.containsKey(((String)key).toLowerCase());

      return super.containsKey(key);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public List<Object> get(Object key)
   {
      if (key != null)
         return super.get(((String)key).toLowerCase());

      return super.get(key);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public List<Object> put(String key, List<Object> value)
   {
      if (key != null)
         key = key.toLowerCase();

      return super.put(key, value);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void putAll(Map<? extends String, ? extends List<Object>> t)
   {
      for (Map.Entry<? extends String, ? extends List<Object>> e : t.entrySet())
         put(e.getKey(), e.getValue());
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public List<Object> remove(Object key)
   {
      if (key != null)
         return super.remove(((String)key).toLowerCase());

      return super.remove(key);
   }

}