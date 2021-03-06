/*****************************************************
 *
 * FacebookImageSource.java
 *
 *
 * Modified MIT License
 *
 * Copyright (c) 2010-2015 Kite Tech Ltd. https://www.kite.ly
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The software MAY ONLY be used with the Kite Tech Ltd platform and MAY NOT be modified
 * to be used with any competitor platforms. This means the software MAY NOT be modified 
 * to place orders with any competitors to Kite Tech Ltd, all orders MUST go through the
 * Kite Tech Ltd platform servers. 
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 *****************************************************/

///// Package Declaration /////

package ly.kite.sample;


///// Import(s) /////

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import ly.kite.R;
import ly.kite.util.Asset;
import ly.kite.facebookphotopicker.FacebookPhotoPicker;
import ly.kite.journey.AImageSource;
import ly.kite.photopicker.common.Photo;


///// Class Declaration /////

/*****************************************************
 *
 * This class represents a local device image source.
 *
 *****************************************************/
public class FacebookImageSource extends AImageSource
  {
  ////////// Static Constant(s) //////////

  @SuppressWarnings( "unused" )
  static private final String  LOG_TAG = "FacebookImageSource";


  ////////// Static Variable(s) //////////


  ////////// Member Variable(s) //////////


  ////////// Static Initialiser(s) //////////


  ////////// Static Method(s) //////////


  ////////// Constructor(s) //////////

  public FacebookImageSource()
    {
    super( R.color.image_source_background_facebook,
            R.drawable.ic_add_facebook_white,
            R.string.image_source_facebook,
            R.id.add_photo_from_facebook,
            R.string.select_photo_from_facebook );
    }


  ////////// AImageSource Method(s) //////////

  /*****************************************************
   *
   * Returns true if the Facebook image source is available.
   * We always assume this is true.
   *
   *****************************************************/
  public boolean isAvailable( Context context )
    {
    return ( true );
    }


  /*****************************************************
   *
   * Called when the image source is picked to select
   * images.
   *
   *****************************************************/
  public void onPick( Fragment fragment, int maxImageCount )
    {
    // TODO: Max image count is ignored currently
    FacebookPhotoPicker.startPhotoPickerForResult( fragment, getActivityRequestCode() );
    }


  /*****************************************************
   *
   * Adds any picked images to the supplied list.
   *
   *****************************************************/
  @Override
  public void getAssetsFromPickerResult( Activity activity, Intent data, IAssetConsumer assetConsumer )
    {
    Photo facebookPhotos[] = FacebookPhotoPicker.getResultPhotos( data );

    if ( facebookPhotos != null )
      {
      // Create an asset list, populate it, and call back to the consumer immediately.

      List<Asset> assetList = new ArrayList<>( facebookPhotos.length );

      for ( Photo facebookPhoto : facebookPhotos )
        {
        assetList.add( new Asset( facebookPhoto.getFullURL() ) );
        }

      assetConsumer.isacOnAssets( assetList );
      }
    }


  ////////// Inner Class(es) //////////

  }

