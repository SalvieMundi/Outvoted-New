//package io.github.how_bout_no.outvoted_new.block;
//
//public class BaseCopperButtonBlock extends ModButtonBlock implements IOxidizable {
//    private final OxidizationLevel oxidizationLevel;
//
//    public BaseCopperButtonBlock(OxidizationLevel oxidizationLevel, Settings settings) {
//        super(settings);
//        this.oxidizationLevel = oxidizationLevel;
//    }
//
//    public OxidizationLevel getDegradationLevel() {
//        return this.oxidizationLevel;
//    }
//
//    @Override
//    protected int getPressTicks() {
//        return 10 * (this.getDegradationLevel().ordinal() + 1);
//    }
//}
