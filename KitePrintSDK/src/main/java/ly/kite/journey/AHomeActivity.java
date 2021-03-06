/*****************************************************
 *
 * AHomeActivity.java
 *
 *
 * Copyright (c) 2015 Kite Tech Ltd. https://www.kite.ly
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

package ly.kite.journey;


///// Import(s) /////

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import ly.kite.R;
import ly.kite.journey.selection.ChooseProductGroupFragment;
import ly.kite.journey.selection.ProductSelectionActivity;
import ly.kite.util.AssetHelper;


///// Class Declaration /////

/*****************************************************
 *
 * This class is the activity for thw Kite SDK sample app.
 * It demonstrates how to create some image assets for
 * personalisation, and then how to start the SDK product
 * selection / shopping journey.
 *
 *****************************************************/
abstract public class AHomeActivity extends ProductSelectionActivity
  {
  ////////// Static Constant(s) //////////

  @SuppressWarnings( "unused" )
  private static final String LOG_TAG                      = "AHomeActivity";

  private static final long   OPEN_MENU_DELAY_MILLIS       = 500L;
  private static final long   CLOSE_MENU_DELAY_MILLIS      = 100L;


  ////////// Static Variable(s) //////////


  ////////// Member Variable(s) //////////

  protected DrawerLayout           mDrawerLayout;
  protected ListView               mNavigationDrawerListView;

  private   boolean                mShowMenuOnce;
  private   Handler                mHandler;

  private   ActionBarDrawerToggle  mDrawerToggle;


  ////////// Static Initialiser(s) //////////


  ////////// Static Method(s) //////////


  ////////// Constructor(s) //////////


  ////////// ProductSelectionActivity Method(s) //////////

  /*****************************************************
   *
   * Called when the activity is created.
   *
   *****************************************************/
  @Override
  public void onCreate( Bundle savedInstanceState )
    {
    super.onCreate( savedInstanceState );


    // Set up the navigation drawer

    mDrawerLayout             = (DrawerLayout)findViewById( R.id.drawer_layout );
    mNavigationDrawerListView = (ListView)findViewById( R.id.navigation_drawer_list_view );

    mDrawerToggle = new ActionBarDrawerToggle( this, mDrawerLayout, R.string.drawer_open, R.string.drawer_closed );
    mDrawerLayout.setDrawerListener( mDrawerToggle );


    // On devices running older versions of Android, there is no menu icon, so the user may not
    // know there's a menu. We want to let them know by showing a hint of the menu.

    if ( savedInstanceState == null && Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2 )
      {
      mHandler = new Handler();

      mShowMenuOnce = true;
      }
    }


  /*****************************************************
   *
   * Called after the activity has been created.
   *
   *****************************************************/
  @Override
  protected void onPostCreate( Bundle savedInstanceState )
    {
    super.onPostCreate( savedInstanceState );

    // Sync the toggle state after onRestoreInstanceState has occurred.
    mDrawerToggle.syncState();
    }


  /*****************************************************
   *
   * Called after the configuration changes.
   *
   *****************************************************/
  @Override
  public void onConfigurationChanged( Configuration newConfig )
    {
    super.onConfigurationChanged( newConfig );

    mDrawerToggle.onConfigurationChanged( newConfig );
    }


  /*****************************************************
   *
   * Called when an action bar item is selected.
   *
   *****************************************************/
  @Override
  public boolean onOptionsItemSelected( MenuItem item )
    {
    // Pass the event to ActionBarDrawerToggle, if it returns
    // true, then it has handled the app icon touch event.

    if ( mDrawerToggle.onOptionsItemSelected( item ) )
      {
      return ( true );
      }


    return ( super.onOptionsItemSelected( item ) );
    }


  /*****************************************************
   *
   * Called with the current top-most fragment.
   *
   *****************************************************/
  protected void onNotifyTop( AKiteFragment topFragment )
    {
    ActionBar actionBar = getActionBar();


    // Determine which fragment is top-most

    String tag = topFragment.getTag();

    if ( tag != null && tag.equals( ChooseProductGroupFragment.TAG ) )
      {
      ///// Home page /////

      // We only enable the menu on the home page
      mDrawerToggle.setDrawerIndicatorEnabled( true );
      mDrawerLayout.setDrawerLockMode( DrawerLayout.LOCK_MODE_UNLOCKED );

      // We display the logo on the home page
      actionBar.setDisplayShowTitleEnabled( false );
      actionBar.setDisplayShowCustomEnabled( true );


      // If we need to show the menu on this devices - start the process now

      if ( mShowMenuOnce )
        {
        mShowMenuOnce = false;


        // Open and close the menu

        mHandler = new Handler();

        mHandler.postDelayed( new OpenMenuRunnable(), OPEN_MENU_DELAY_MILLIS );
        }

      }
    else
      {
      mDrawerToggle.setDrawerIndicatorEnabled( false );
      mDrawerLayout.setDrawerLockMode( DrawerLayout.LOCK_MODE_LOCKED_CLOSED );

      // On other pages we show a title
      actionBar.setDisplayShowTitleEnabled( true );
      actionBar.setDisplayShowCustomEnabled( false );
      }


    super.onNotifyTop( topFragment );
    }


  /*****************************************************
   *
   * Called when an activity result is received.
   *
   *****************************************************/
  @Override
  protected void onActivityResult( int requestCode, int resultCode, Intent data )
    {
    // For the custom apps, we don't want to exit the activity once check-out is
    // complete - we want to go back to the landing page - the choose product group
    // fragment.

    if ( resultCode == ACTIVITY_RESULT_CODE_CHECKED_OUT )
      {
      mFragmentManager.popBackStackImmediate( ChooseProductGroupFragment.TAG, 0 );

      // If we have successfully completed a check-out, then also clear out any cached
      // assets. (This won't clear any product images).
      AssetHelper.clearSessionAssets( this );

      return;
      }


    super.onActivityResult( requestCode, resultCode, data );
    }


  ////////// Method(s) //////////

  /*****************************************************
   *
   * ...
   *
   *****************************************************/


  ////////// Inner Class(es) //////////

  /*****************************************************
   *
   * Runnable to open the menu.
   *
   *****************************************************/
  private class OpenMenuRunnable implements Runnable, DrawerLayout.DrawerListener
    {
    public void run()
      {
      // Override the drawer listener so we know when it has fully opened.
      mDrawerLayout.setDrawerListener( this );

      mDrawerLayout.openDrawer( Gravity.LEFT );
      }

    @Override
    public void onDrawerSlide( View drawerView, float slideOffset )
      {
      // Ignore
      }

    @Override
    public void onDrawerOpened( View drawerView )
      {
      // We no longer need to listen for drawer events
      mDrawerLayout.setDrawerListener( null );

      mHandler.postDelayed( new CloseMenuRunnable(), CLOSE_MENU_DELAY_MILLIS );
      }

    @Override
    public void onDrawerClosed( View drawerView )
      {
      // Ignore
      }

    @Override
    public void onDrawerStateChanged( int newState )
      {
      // Ignore
      }
    }


  /*****************************************************
   *
   * Runnable to close the menu.
   *
   *****************************************************/
  private class CloseMenuRunnable implements Runnable
    {
    public void run()
      {
      mDrawerLayout.closeDrawers();
      }
    }


  }
