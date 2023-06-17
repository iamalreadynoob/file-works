package flareManipulation;

import java.util.ArrayList;

public class Dimension
{

    private int layers;
    private ArrayList<Integer> layerSizes;

    public Dimension(ArrayList<Integer> layerSizes)
    {
        this.layerSizes = layerSizes;
        layers = layerSizes.size();
    }

    public int getLayers() {return layers;}
    public ArrayList<Integer> getLayerSizes() {return layerSizes;}

    public void appendLayer(Integer size)
    {
        layers++;
        layerSizes.add(size);
    }

    public void addLayer(int index, Integer size)
    {
        layers++;
        int lastIndex = layerSizes.size() - 1;
        layerSizes.add(null);

        for (int i = lastIndex; i >= index; i--) layerSizes.set(i+1, layerSizes.get(i));
        layerSizes.set(index, size);
    }

    public void removeLayer(int index)
    {
        layers--;
        layerSizes.remove(index);
    }

    public void setLayerSize(int layer, Integer size)
    {
        layerSizes.set(layer, size);
    }
}
