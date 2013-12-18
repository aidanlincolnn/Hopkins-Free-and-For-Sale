package oose.group13.hffs.data;


/** The states that an offer can be in 
 * @author aidanfowler
 */

public enum OfferState
{
    /** The offer was accepted and is now a completed offer */
    ACCEPTED,
    /** The offer was declined */
    DECLINED,
    /** The offer is still open and has not been declined or accepted yet */
    OPEN,
    /** The offer has been countered*/
    COUNTERED;
}