Recipe Browser is not functioning in a way that we like. Recipe Browser is quite limited in its functionality, including inability to browse by ingredients, which we had originally wanted. Additionally, browsing by tag is not functioning in a desired way. Recipes are being filtered by tags with a *union* of tags that match, not an *intersection* as intended. Next, in the browser view, the tags list tends to get cut off at the end by the recipe list. Finally, we wish to have thumbnail photos for the recipes in the browser. Unfortunately, due to photos not yet being implemented, we need to first achieve that goal before we can add thumbnail photos

We intend to achieve these goals by doing the following:
- adding dynamic spacing to the tag list
- changing the method of tag-checking to containing all tags instead of checking each tag separately
- allowing a toggle between search-by-name and search-by-ingredient enforced by an if-else implementation
- implement entire devtask set pertaining to photos, to provide full functionality of adding photos to recipes
- load top photo from recipe into the browser view

These goals will be measured use simple matter-of-fact observation of desired behaviour.
- tag list should continue to push down recipe list as more are added
- adding tags to the search should *reduce* the number appearing instead of *expanding*
- search-by-name and search-by-ingredient should both pass acceptance tests and be mutually exclusive. This will be ensured by searching one way, toggling, and using the other.
- recipe thumbnails are easily verifiable by observation as well. If the photo appears in browser and fits within the recipe's info card, then we have succeeded.
- the photo which is first in a recipe's list must always be the one that appears as the thumbnail. To verify this, we will test by deleting the top photo and returning to browser to ensure the change is reflected.
