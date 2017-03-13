/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

// Server-side JavaScript for the topnav logic
use(function () {
    var items = [];
    var root = currentPage.getAbsoluteParent(2);
    var currentNavPath = currentPage.getAbsoluteParent(2).getPath();

       var REDIRECT_RESOURCE_TYPE = "foundation/components/redirect";
    var PROP_REDIRECT_TARGET = "redirectTarget";

    var PROP_HIDE_IN_NAV = "hideInNav";
    var PROP_HIDE_SUB_IN_NAV = "hideSubItemsInNav";

    var PROP_NAV_ROOT = "navRoot";


/**
     * Get list of pages
     * _root - root node to start listing from
     * level - how deep to get into the tree
     */
    var getPages = function(_root, level) {
        if (level === 0 || !_root) {
            return null;
        }
        var it = _root.listChildren(new Packages.com.day.cq.wcm.api.PageFilter());
        var _items = [], page, selected, pageContentResource, pageValueMap, pagePath, children;

        while (it.hasNext()) {
            page = it.next();
            pageContentResource = page.getContentResource();
            pageValueMap = pageContentResource.adaptTo(org.apache.sling.api.resource.ValueMap);

            if (pageValueMap.get(PROP_HIDE_IN_NAV, java.lang.Boolean)) {
                continue;
            }

            if (REDIRECT_RESOURCE_TYPE.equals(pageContentResource.getResourceType())) {
                page = resolveRedirect(pageValueMap);
            }

            selected = (currentNavPath && page && currentNavPath.contains(page.getPath()));

            _items.push({
                page: page,
                selected: selected,
                children: pageValueMap.get(PROP_HIDE_SUB_IN_NAV, java.lang.Boolean) ? [] : getPages(page, level - 1)
            });
        }

        return _items;
    };  

    items = getPages(root, 3);//root.listChildren(new Packages.com.day.cq.wcm.api.PageFilter());

    /*while (it.hasNext()) {
        var page = it.next();

        // No strict comparison, because the types returned from the Java APIs
        // don't strictly match the JavaScript types
        var selected = (page.getPath() == currentNavPath);

        items.push({
            page: page,
            selected : selected
        });
    }
*/
    return {
        items: items
    };


});