/*****************************************************
 *
 * SingleCurrencyAmount.java
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

package ly.kite.catalogue;


///// Import(s) /////


///// Class Declaration /////

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

/*****************************************************
 *
 * This class represents an amount in a single currency.
 *
 *****************************************************/
public class SingleCurrencyAmount implements Parcelable
  {
  ////////// Static Constant(s) //////////

  @SuppressWarnings( "unused" )
  private static final String  LOG_TAG                     = "SingleCurrencyAmount";

  private static final String  FORMAL_AMOUNT_FORMAT_STRING = "%1$s %2$.2f";


  ////////// Static Variable(s) //////////

  public static final Parcelable.Creator<SingleCurrencyAmount> CREATOR =
    new Parcelable.Creator<SingleCurrencyAmount>()
      {
      public SingleCurrencyAmount createFromParcel( Parcel sourceParcel )
        {
        return ( new SingleCurrencyAmount( sourceParcel ) );
        }

      public SingleCurrencyAmount[] newArray( int size )
        {
        return ( new SingleCurrencyAmount[ size ] );
        }
      };


  ////////// Member Variable(s) //////////

  private Currency    mCurrency;
  private BigDecimal  mAmount;
  private String      mFormattedAmount;


  ////////// Static Initialiser(s) //////////


  ////////// Static Method(s) //////////


  ////////// Constructor(s) //////////

  /*****************************************************
   *
   * Constructs a new single currency amount.
   *
   * @param currency        The currency that the amount is in.
   * @param amount          A BigDecimal representing the amount.
   * @param formattedAmount A string representation of the amount.
   *
   * @throws IllegalArgumentException if either the currency
   *         or the amount is null.
   *
   *****************************************************/
  public SingleCurrencyAmount( Currency currency, BigDecimal amount, String formattedAmount )
    {
    if ( currency == null ) throw ( new IllegalArgumentException( "Currency must be supplied" ) );
    if ( amount   == null ) throw ( new IllegalArgumentException( "Amount must be supplied" ) );

    mCurrency        = currency;
    mAmount          = amount;
    mFormattedAmount = formattedAmount;
    }


  /*****************************************************
   *
   * Constructs a new single currency amount, with no formatted
   * amount.
   *
   * @param currency        The currency that the amount is in.
   * @param amount          A BigDecimal representing the amount.
   *
   * @throws IllegalArgumentException if either the currency
   *         or the amount is null.
   *
   *****************************************************/
  public SingleCurrencyAmount( Currency currency, BigDecimal amount )
    {
    this ( currency, amount, null );
    }


  // Constructor used by parcelable interface
  SingleCurrencyAmount( Parcel parcel )
    {
    mCurrency        = (Currency)parcel.readSerializable();
    mAmount          = (BigDecimal)parcel.readSerializable();
    mFormattedAmount = parcel.readString();
    }


  ////////// Parcelable Method(s) //////////

  /*****************************************************
   *
   * Describes the contents.
   *
   *****************************************************/
  @Override
  public int describeContents()
    {
    return ( 0 );
    }


  /*****************************************************
   *
   * Write the contents to a parcel.
   *
   *****************************************************/
  @Override
  public void writeToParcel( Parcel targetParcel, int flags )
    {
    targetParcel.writeSerializable( mCurrency );
    targetParcel.writeSerializable( mAmount );
    targetParcel.writeString( mFormattedAmount );
    }


  ////////// Method(s) //////////

  /*****************************************************
   *
   * Returns the currency.
   *
   *****************************************************/
  public Currency getCurrency()
    {
    return ( mCurrency );
    }


  /*****************************************************
   *
   * Returns the currency code.
   *
   *****************************************************/
  public String getCurrencyCode()
    {
    return ( mCurrency.getCurrencyCode() );
    }


  /*****************************************************
   *
   * Returns the amount.
   *
   *****************************************************/
  public BigDecimal getAmount()
    {
    return ( mAmount );
    }


  /*****************************************************
   *
   * Returns true if the amount is zero, false otherwise.
   *
   *****************************************************/
  public boolean isZero()
    {
    return ( mAmount.compareTo( BigDecimal.ZERO ) == 0 );
    }


  /*****************************************************
   *
   * Returns true if the amount is non-zero, false otherwise.
   *
   *****************************************************/
  public boolean isNonZero()
    {
    return ( mAmount.compareTo( BigDecimal.ZERO ) != 0 );
    }


  /*****************************************************
   *
   * Returns the amount as a double.
   *
   *****************************************************/
  public double getAmountAsDouble()
    {
    return ( mAmount.doubleValue() );
    }


  /*****************************************************
   *
   * Returns the formatted.
   *
   *****************************************************/
  public String getFormattedAmount()
    {
    return ( mFormattedAmount );
    }


  /*****************************************************
   *
   * Returns the amount as a displayable string for the
   * supplied locale.
   *
   * If our currency is the same as the main currency in use
   * for the supplied locale, then we use the number formatter
   * to format the amount - which will give a localised string.
   *
   * If the currency is different, then we format the amount with
   * the full currency code. We do this to avoid any ambiguity.
   * For example, if we were to live in Sweden but found a cost
   * in Danish Krone, then having an amount such as "4.00 kr"
   * would be misleading (because we would believe we were being
   * quoted in Swedish Kronor).
   *
   * @param locale The locale for which a display amount is required.
   *               If the locale is not supplied or suported by the device,
   *               the display amount is always shown with a currency prefix.
   *
   *****************************************************/
  public String getDisplayAmountForLocale( Locale locale )
    {
    if ( locale != null )
      {
      // The locale may not be supported by this device.
      try
        {
        // Get the currency used by the locale
        Currency localeCurrency = Currency.getInstance( locale );

        // If the currency matches the current locale's currency, use the number formatter to
        // format the amount.
        if ( mCurrency.equals( localeCurrency ) )
          {
          NumberFormat numberFormatter = NumberFormat.getCurrencyInstance( locale );

          return ( numberFormatter.format( getAmountAsDouble() ) );
          }
        }
      catch ( IllegalArgumentException iae )
        {
        // Fall through
        }
      }


    // Format the amount formally
    return ( String.format( Locale.getDefault(), FORMAL_AMOUNT_FORMAT_STRING, mCurrency.getCurrencyCode(), getAmountAsDouble() ) );
    }


  /*****************************************************
   *
   * Returns this amount multiplied by the supplied quantity.
   *
   *****************************************************/
  public SingleCurrencyAmount multipliedBy( int quantity )
    {
    return ( new SingleCurrencyAmount( mCurrency, mAmount.multiply( BigDecimal.valueOf( quantity ) ) ) );
    }


  ////////// Inner Class(es) //////////

  /*****************************************************
   *
   * ...
   *
   *****************************************************/

  }

