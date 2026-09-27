/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transfer.util;

/**
 *
 * @author Asus
 */
public interface Operation {

    public static final int LOGIN = 0;
    public static final int LOGOUT = 1;

    public static final int ADD_KLIJENT = 2;
    public static final int DELETE_KLIJENT = 3;
    public static final int UPDATE_KLIJENT = 4;
    public static final int GET_ALL_KLIJENT = 5;

    public static final int ADD_USLUGA = 6;
    public static final int DELETE_USLUGA = 7;
    public static final int UPDATE_USLUGA = 8;
    public static final int GET_ALL_USLUGA = 9;

    public static final int ADD_TERMIN = 10;
    public static final int DELETE_TERMIN = 11;
    public static final int UPDATE_TERMIN = 12;
    public static final int GET_ALL_TERMIN = 13;

    public static final int GET_ALL_TIP_KLIJENTA = 14;

}
