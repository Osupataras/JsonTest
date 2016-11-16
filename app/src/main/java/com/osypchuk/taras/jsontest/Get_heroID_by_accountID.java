package com.osypchuk.taras.jsontest;

/**
 * Created by Taras on 27.10.2016.
 */

public class Get_heroID_by_accountID {

    public static boolean accontID_confirming (String players_accountID,String my_accountID){
        if(players_accountID.equals(my_accountID)){return true;}
        else {return false;}
    }


    public static String Get_heroNAME_by_heroId (int heroID){
        String heroNAME = "";
        switch (heroID){
            case 1: heroNAME = "antimage";break;
            case 2: heroNAME = "axe";      break;
            case 3: heroNAME = "bane";break;
            case 4: heroNAME = "bloodseeker";break;
            case 5: heroNAME = "crystal_maiden";break;
            case 6: heroNAME = "drow_ranger";         break;
            case 7: heroNAME = "earthshaker";break;
            case 8: heroNAME = "juggernaut";break;
            case 9: heroNAME = "mirana";break;
            case 11: heroNAME = "nevermore";  break;
            case 10: heroNAME = "morphing";break;
            case 12: heroNAME = "phantom_lancer";break;
            case 13: heroNAME = "puck";break;
            case 14: heroNAME = "pudge" ;break;
            case 15: heroNAME = "razor";break;
            case 16: heroNAME = "sand_king";break;
            case 17: heroNAME = "stormspirit";break;
            case 18: heroNAME = "sven";break;
            case 19: heroNAME = "tiny";break;
            case 20: heroNAME = "vengefulspirit";break;
            case 21: heroNAME = "windrunner";break;
            case 22: heroNAME = "zuus";break;
            case 23: heroNAME = "kunkka";break;
            case 25: heroNAME = "lina";break;
            case 31: heroNAME = "lich";break;
            case 26: heroNAME = "lion";break;
            case 27: heroNAME = "shadow_shaman";break;
            case 28: heroNAME = "slardar";break;
            case 29: heroNAME = "tidehunter";break;
            case 30: heroNAME = "witchdoctor";break;
            case 32: heroNAME = "riki";break;
            case 33: heroNAME = "enigma";break;
            case 34: heroNAME = "tinker";break;
            case 35: heroNAME = "sniper";break;
            case 36: heroNAME = "necrolyte";break;
            case 37: heroNAME = "warlok";break;
            case 38: heroNAME = "beastaster";break;
            case 39: heroNAME = "queenfpain";break;
            case 40: heroNAME = "venomncer";break;
            case 41: heroNAME = "facelss_void";break;
            case 42: heroNAME = "skeleon_king";break;
            case 43: heroNAME = "deathprophet";break;
            case 44: heroNAME = "phantom_assassin";break;
            case 45: heroNAME = "pugna";break;
            case 46: heroNAME = "templr_assassin";break;
            case 47: heroNAME = "viper";break;
            case 48: heroNAME = "luna";break;
            case 49: heroNAME = "drago_knight";break;
            case 50: heroNAME = "dazzl";break;
            case 51: heroNAME = "rattltrap";break;
            case 52: heroNAME = "leshrc";break;
            case 53: heroNAME = "furio";break;
            case 54: heroNAME = "life_tealer";break;
            case 55: heroNAME = "dark_eer";break;
            case 56: heroNAME = "clink";break;
            case 57: heroNAME = "omnikight";break;
            case 58: heroNAME = "enchatress";break;
            case 59: heroNAME = "huska";break;
            case 60: heroNAME = "nightstalker";break;
            case 61: heroNAME = "broodother";break;
            case 62: heroNAME = "bount_hunter";break;
            case 63: heroNAME = "weave";break;
            case 64: heroNAME = "jakir";break;
            case 65: heroNAME = "batrier";break;
            case 66: heroNAME = "chen";break;
            case 67: heroNAME = "specte";break;
            case 69: heroNAME = "doom_ringer";break;
            case 68: heroNAME = "anciet_apparition";break;
            case 70: heroNAME = "ursa";break;
            case 71: heroNAME = "spiri_breaker";break;
            case 72: heroNAME = "gyrocpter";break;
            case 73: heroNAME = "alcheist";break;
            case 74: heroNAME = "invokr";break;
            case 75: heroNAME = "silencer";break;
            case 76: heroNAME = "obsidian_destroyer";break;
            case 77: heroNAME = "lycan";break;
            case 78: heroNAME = "brewmster";break;
            case 79: heroNAME = "shadow_demon";break;
            case 80: heroNAME = "lone_ruid";break;
            case 81: heroNAME = "chaosknight";break;
            case 82: heroNAME = "meepo";break;
            case 83: heroNAME = "treant";break;
            case 84: heroNAME = "ogre_agi";break;
            case 85: heroNAME = "undyig";break;
            case 86: heroNAME = "rubic";break;
            case 87: heroNAME = "disrutor";break;
            case 88: heroNAME = "nyx_asassin";break;
            case 89: heroNAME = "naga_iren";break;
            case 90: heroNAME = "keepe_of_the_light";break;
            case 91: heroNAME = "wisp";break;
            case 92: heroNAME = "visag";break;
            case 93: heroNAME = "slark";break;
            case 94: heroNAME = "medus";break;
            case 95: heroNAME = "troll_warlord";break;
            case 96: heroNAME = "centar";break;
            case 97: heroNAME = "magnaaur";break;
            case 98: heroNAME = "shredder";break;
            case 99: heroNAME = "bristeback";break;
            case 100: heroNAME = "tusk";break;
            case 101: heroNAME = "skywrath_mage";break;
            case 102: heroNAME = "abaddn";break;
            case 103: heroNAME = "eldertitan";break;
            case 104: heroNAME = "legion_commander";break;
            case 106: heroNAME = "emberspirit";break;
            case 107: heroNAME = "earthspirit";break;
            case 108: heroNAME = "abyssl_underlord";break;
            case 109: heroNAME = "terroblade";break;
            case 110: heroNAME = "phoenx";break;
            case 105: heroNAME = "techis";break;
            case 111: heroNAME = "oracl";break;
            case 112: heroNAME = "winte_wyvern";break;
            case 113: heroNAME = "arc_wrden";break;
        }
        return heroNAME;
    }


}
