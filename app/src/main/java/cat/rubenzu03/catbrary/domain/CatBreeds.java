package cat.rubenzu03.catbrary.domain;

public enum CatBreeds {
    Abyssinian("Abyssinian"),
    AmericanBobtail("American Bobtail"),
    Aegean("Aegean"),
    AmericanShorthair("American Shorthair"),
    AmericanWirehair("American Wirehair"),
    AustralianMist("Australian Mist"),
    ArabianMau("Arabian Mau"),
    Balinese("Balinese"),
    Bambino("Bambino"),
    Bengal("Bengal"),
    Birman("Birman"),
    Bombay("Bombay"),
    BritishLonghair("British Longhair"),
    Burmese("Burmese"),
    BritishShorthair("British Shorthair"),
    Burmilla("Burmilla"),
    CaliforniaSpangled("California Spangled"),
    Chausie("Chausie"),
    ChantillyTiffany("Chantilly Tiffany"),
    Cheetoh("Cheetoh"),
    ColorpointShorthair("Colorpoint Shorthair"),
    CornishRex("Cornish Rex"),
    Cymric("Cymric"),
    Cyprus("Cyprus"),
    DevonRex("Devon Rex"),
    Donskoy("Donskoy"),
    DragonLi("Dragon Li"),
    EgyptianMau("Egyptian Mau"),
    ExoticShorthair("Exotic Shorthair"),
    HavanaBrown("Havana Brown"),
    Himalayan("Himalayan"),
    JapaneseBobtail("Japanese Bobtail"),
    Javanese("Javanese"),
    KhaoManee("Khao Manee"),
    Korat("Korat"),
    Kurilian("Kurilian"),
    LaPerm("LaPerm"),
    MaineCoon("Maine Coon"),
    Manx("Manx"),
    Munchkin("Munchkin"),
    Nebelung("Nebelung"),
    NorwegianForest("Norwegian Forest"),
    Ocicat("Ocicat"),
    Oriental("Oriental"),
    Persian("Persian"),
    PixieBob("Pixie Bob"),
    Ragamuffin("Ragamuffin"),
    Ragdoll("Ragdoll"),
    RussianBlue("Russian Blue"),
    Savannah("Savannah"),
    ScottishFold("Scottish Fold"),
    SelkirkRex("Selkirk Rex"),
    Siamese("Siamese"),
    Siberian("Siberian"),
    Singapura("Singapura"),
    Snowshoe("Snowshoe"),
    Somali("Somali"),
    Sphynx("Sphynx"),
    Tonkinese("Tonkinese"),
    Toyger("Toyger"),
    TurkishAngora("Turkish Angora"),
    TurkishVan("Turkish Van"),
    YorkChocolate("York Chocolate"),
    NONE("None");

    private final String displayName;

    CatBreeds(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
