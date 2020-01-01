package thelarsinator.extfar.animals.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thelarsinator.extfar.animals.goals.GoatGoToMilkstationGoal;
import thelarsinator.extfar.registry.EntityRegistry;
import thelarsinator.extfar.registry.ItemRegistry;

public class GoatEntity extends AnimalEntity {
    public GoatEntity(EntityType<? extends GoatEntity> type, World worldIn) {
        super(type, worldIn);
    }
    private static final DataParameter<Boolean> HAS_MILK = EntityDataManager.createKey(CatEntity.class, DataSerializers.BOOLEAN);
    private EatGrassGoal eatGrassGoal;


    protected void registerData() {
        super.registerData();
        this.dataManager.register(HAS_MILK, false);
    }

    protected void registerGoals() {
        this.eatGrassGoal = new EatGrassGoal(this);
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new GoatGoToMilkstationGoal(this, 0.8D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.fromItems(Items.WHEAT), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(5, this.eatGrassGoal);
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.25F);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SHEEP_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_SHEEP_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SHEEP_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_SHEEP_STEP, 0.15F, 1.0F);
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float getSoundVolume() {
        return 0.4F;
    }

    public GoatEntity createChild(AgeableEntity ageable) {
        return EntityRegistry.goat.create(this.world);
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.isChild() ? sizeIn.height * 0.95F : 1.3F;
    }

    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        if (itemstack.getItem() == Items.BUCKET && !player.abilities.isCreativeMode && !this.isChild()) {
            player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
            itemstack.shrink(1);
            if (itemstack.isEmpty()) {
                player.setHeldItem(hand, new ItemStack(ItemRegistry.goat_milk_bucket));
            } else if (!player.inventory.addItemStackToInventory(new ItemStack(ItemRegistry.goat_milk_bucket))) {
                player.dropItem(new ItemStack(ItemRegistry.goat_milk_bucket), false);
            }

            return true;
        } else {
            return super.processInteract(player, hand);
        }
    }

    /**
     * This function applies the benefits of growing back wool and faster growing up to the acting entity. (This function
     * is used in the AIEatGrass)
     */
    public void eatGrassBonus() {
        this.dataManager.set(HAS_MILK, true);
    }

    public boolean getMilkedByMachine(World worldIn){
        if(this.dataManager.get(HAS_MILK)){
            this.dataManager.set(HAS_MILK, false);
            return true;
        }
        return false;
    }

    public boolean hasMilk(){
        return this.dataManager.get(HAS_MILK);
    }
}