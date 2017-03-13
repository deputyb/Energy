package com.nvenergy.nvenergy.core.models;

import org.apache.felix.scr.annotations.Reference;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.settings.SlingSettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * Class for the Base Component
 */
@Model(adaptables = SlingHttpServletRequest.class) public abstract class BaseComponent {

    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Inject @Reference public SlingSettingsService settingsService;
    @Inject @Self private SlingHttpServletRequest request;

    private boolean author;

    @PostConstruct abstract protected void init();

    protected void prepare() {
        LOG.info("BaseComponent init begin");
        author = settingsService.getRunModes().contains("author");
        LOG.debug("IsAuthor?: {}", author);
        LOG.info("BaseComponent init finish");
    }

    public boolean isAuthor() {
        return author;
    }
}
