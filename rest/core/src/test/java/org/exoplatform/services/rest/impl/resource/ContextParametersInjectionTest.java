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
package org.exoplatform.services.rest.impl.resource;

import org.exoplatform.services.rest.AbstractResourceTest;
import org.exoplatform.services.rest.impl.MultivaluedMapImpl;
import org.exoplatform.services.rest.impl.header.HeaderHelper;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Providers;

/**
 * @author <a href="mailto:andrew00x@gmail.com">Andrey Parfonov</a>
 * @version $Id: $
 */
public class ContextParametersInjectionTest extends AbstractResourceTest
{

   @Path("/a/b")
   public static class Resource1
   {

      @GET
      @Path("c")
      public String m0(@Context UriInfo uriInfo)
      {
         return uriInfo.getRequestUri().toString();
      }

      @GET
      @Path("d")
      public String m1(@Context HttpHeaders headers)
      {
         List<String> l = headers.getRequestHeader("Accept");
         return HeaderHelper.convertToString(l);
      }

      @GET
      @Path("e")
      public String m2(@Context Request request)
      {
         return request.getMethod();
      }

      @GET
      @Path("f")
      public void m3(@Context Providers providers)
      {
         assertNotNull(providers);
      }

   }

   public void testMethodContextInjection() throws Exception
   {
      Resource1 r1 = new Resource1();
      registry(r1);
      injectionTest();
      unregistry(r1);
   }

   //--------------------

   @Path("/a/b")
   public static class Resource2
   {

      @Context
      private UriInfo uriInfo;

      @Context
      private HttpHeaders headers;

      @Context
      private Request request;

      @Context
      private Providers providers;

      @GET
      @Path("c")
      public String m0()
      {
         return uriInfo.getRequestUri().toString();
      }

      @GET
      @Path("d")
      public String m1()
      {
         List<String> l = headers.getRequestHeader("Accept");
         return HeaderHelper.convertToString(l);
      }

      @GET
      @Path("e")
      public String m2()
      {
         return request.getMethod();
      }

      @GET
      @Path("g")
      public void m4()
      {
         assertNotNull(providers);
      }
   }

   public void testFieldInjection() throws Exception
   {
      registry(Resource2.class);
      injectionTest();
      unregistry(Resource2.class);
   }

   //--------------------

   @Path("/a/b")
   public static class Resource3
   {

      private UriInfo uriInfo;

      private HttpHeaders headers;

      private Request request;

      private Providers providers;

      public Resource3(@Context UriInfo uriInfo, @Context HttpHeaders headers, @Context Request request,
         @Context Providers providers)
      {
         this.uriInfo = uriInfo;
         this.headers = headers;
         this.request = request;
         this.providers = providers;
      }

      @GET
      @Path("c")
      public String m0()
      {
         return uriInfo.getRequestUri().toString();
      }

      @GET
      @Path("d")
      public String m1()
      {
         List<String> l = headers.getRequestHeader("Accept");
         return HeaderHelper.convertToString(l);
      }

      @GET
      @Path("e")
      public String m2()
      {
         return request.getMethod();
      }

      @GET
      @Path("g")
      public void m4()
      {
         assertNotNull(providers);
      }
   }

   public void testConstructorInjection() throws Exception
   {
      registry(Resource3.class);
      injectionTest();
      unregistry(Resource3.class);
   }

   //

   private void injectionTest() throws Exception
   {
      assertEquals("http://localhost/test/a/b/c", service("GET", "http://localhost/test/a/b/c",
         "http://localhost/test", null, null).getEntity());
      MultivaluedMap<String, String> h = new MultivaluedMapImpl();
      h.add("Accept", "text/xml");
      h.add("Accept", "text/plain;q=0.7");
      assertEquals("text/xml,text/plain;q=0.7", service("GET", "http://localhost/test/a/b/d", "http://localhost/test",
         h, null).getEntity());
      assertEquals("GET", service("GET", "http://localhost/test/a/b/e", "http://localhost/test", null, null)
         .getEntity());
      service("GET", "http://localhost/test/a/b/f", "http://localhost/test", null, null).getEntity();
   }

}