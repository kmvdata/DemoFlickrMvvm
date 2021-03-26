# DemoFlickrMvvm
This project shows how to build an application based on fragment-ktx. It also demonstrates how to use viewpager2, retrofit2, glide and so on.

## MainActivity and MainFragemnt

MainActivity and MainFragment is created by default. MainFragemnt is based on MainActivity, and it contains a viewPager of type ViewPager2.

A BottomNavigationView object is at the bottom of MainFragment. This navigation contains three menu items: Talk, Flickr Photo and Mine.

When the menu item is selected, viewPager will set its current item to the corresponding fragment page.

## Talk Page

Talk page shows how to layout a list of talk info in  RecyclerView. And the talk icon is fetched from Flickr with Glide.

## Flickr Photo Page

Flickr photo page shows how to layout images in a 3x3 grid whith GridLayoutManager.

## Mine Page

Mine page shows how to add item decoration to a cell, and add a click animation on it by setting its background to "?android:attr/selectableItemBackground".
