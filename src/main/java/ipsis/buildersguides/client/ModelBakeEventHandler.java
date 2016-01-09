package ipsis.buildersguides.client;

import ipsis.buildersguides.client.model.ISBMMarker;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelBakeEventHandler {

    @SubscribeEvent
    public void onModelBakeEvent(ModelBakeEvent event) {

        Object object = event.modelRegistry.getObject(ISBMMarker.modelResourceLocation);
        if (object != null) {
            ISBMMarker customModel = new ISBMMarker();
            event.modelRegistry.putObject(ISBMMarker.modelResourceLocation, customModel);
        }
    }
}
