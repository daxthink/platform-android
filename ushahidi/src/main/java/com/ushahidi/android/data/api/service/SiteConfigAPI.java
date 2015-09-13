package com.ushahidi.android.data.api.service;

import com.ushahidi.android.domain.entity.Config;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * This interface has the api for getting the site config information from API + /api/v3/config/site
 *
 * Intended to be used to get the title of the url (and also a way to verify that the url is a valid
 * ushahidi platform url) at the time the user enters/updates the deployment information.
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public interface SiteConfigAPI {

    @GET("/api/v3/config/site")
    void getConfig(Callback<Config> response);

}