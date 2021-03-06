/*****************************************************
 *
 * CustomTypefaceSetter.java
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

package ly.kite.widget;


///// Import(s) /////


///// Class Declaration /////

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import ly.kite.R;

/*****************************************************
 *
 * This class sets a custom typeface for components.
 *
 *****************************************************/
public class CustomTypefaceSetter
  {
  ////////// Static Constant(s) //////////

  @SuppressWarnings( "unused" )
  static private final String  LOG_TAG = "CustomTypefaceSetter";


  ////////// Static Variable(s) //////////


  ////////// Member Variable(s) //////////


  ////////// Static Initialiser(s) //////////


  ////////// Static Method(s) //////////

  /*****************************************************
   *
   * Sets the typeface for a component.
   *
   *****************************************************/
  static public void setTypeface( Context context, TextView textView )
    {
    if ( textView == null ) return;


    // If a custom typeface has been defined in the resources - try to use it, but make sure
    // we keep the current style.

    String customFont = context.getResources().getString( R.string.custom_typeface_file_name );

    if ( customFont != null && ! customFont.equals( "" ) )
      {
      Typeface customTypeface = TypefaceCache.getTypeface( context, customFont );

      if ( customTypeface != null )
        {
        Typeface currentTypeface = textView.getTypeface();

        if ( currentTypeface != null )
          {
          int style = currentTypeface.getStyle();

          textView.setTypeface( customTypeface, style );
          }
        else
          {
          textView.setTypeface( customTypeface );
          }

        }

      }

    }


  ////////// Constructor(s) //////////


  ////////// Method(s) //////////

  /*****************************************************
   *
   * ...
   *
   *****************************************************/


  ////////// Inner Class(es) //////////

  /*****************************************************
   *
   * ...
   *
   *****************************************************/

  }

