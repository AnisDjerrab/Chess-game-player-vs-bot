import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.GVTBuilder;
import org.apache.batik.bridge.UserAgentAdapter;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Document;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.awt.event.*;
import java.util.LinkedHashMap;
import javax.swing.border.Border;
import java.util.*;
//javac -cp "lib/*;." jeuDechec.java
//java -cp "lib/*;." jeuDechec
public class jeuDechec {
    public BufferedImage convetirSVG(String path, int width, int height) {
        try {
            String parser = XMLResourceDescriptor.getXMLParserClassName();
            SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);
            Document svgDocument = factory.createDocument(new File(path).toURI().toString());
            GVTBuilder builder = new GVTBuilder();
            BridgeContext ctx = new BridgeContext(new UserAgentAdapter());
            ctx.setDynamicState(BridgeContext.DYNAMIC);
            GraphicsNode svgGraphics = builder.build(ctx, svgDocument);
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            svgGraphics.paint(g2d);
            g2d.dispose();
            return image;
        } catch (Exception e) {
            System.out.println("failed at creating the image : " + path);
            e.printStackTrace();
            return null;
        }
    }
    public JLabel creerJLabel(String path, int width, int height) {
        jeuDechec ex = new jeuDechec();
        System.gc();
        BufferedImage svgimage = ex.convetirSVG(path, width, height);
        if (svgimage != null) {
            ImageIcon imageicon = new ImageIcon(svgimage);
            JLabel label = new JLabel(imageicon);
            label.setPreferredSize(new Dimension(imageicon.getIconWidth(), imageicon.getIconHeight()));
            return label;
        }
        else {
            System.out.println("failed at reading the image");
            return null;
        }
    }
    public List<Integer> calculeConcernedCases(Map<JPanel, List<Integer>> carte, List<Integer> element) {
        List<Integer> elementsConcernes = new ArrayList<>();
        if (element.get(0) == 24) {
            int nombre = 0;
            for (JPanel panneau : carte.keySet()) {
                if (carte.get(panneau).get(2).equals(element.get(2) - 100) && carte.get(panneau).get(1).equals(element.get(1)) && carte.get(panneau).get(0).equals(0)) {
                    elementsConcernes.add(nombre);
                    if (element.get(2) == 700) {
                        int n = 0;
                        for (JPanel thing : carte.keySet()) {
                            if (carte.get(thing).get(2).equals(element.get(2) - 200) && carte.get(thing).get(1).equals(element.get(1)) && carte.get(thing).get(0).equals(0)) {
                                elementsConcernes.add(n);
                                break;
                            }
                            n++;
                        }
                    }
                    break;
                } 
                nombre += 1;
            }
        } else if (element.get(0) == 20) {
            List<List<Boolean>> Array = new ArrayList<>();
            Array.add(new ArrayList<>(Arrays.asList(true, true)));
            Array.add(new ArrayList<>(Arrays.asList(true, true)));
            Array.add(new ArrayList<>(Arrays.asList(true, true)));
            Array.add(new ArrayList<>(Arrays.asList(true, true)));
            for (int i = 1; i < 9; i++) {
                int nombre = 0;
                Array.get(0).set(1, false);
                Array.get(1).set(1, false);
                Array.get(2).set(1, false);
                Array.get(3).set(1, false);
                for (JPanel panneau : carte.keySet()) {
                    if (carte.get(panneau).get(2).equals(element.get(2) + (i*100)) && carte.get(panneau).get(1).equals(element.get(1)) && carte.get(panneau).get(0).equals(0)) {
                        if (Array.get(0).get(0).equals(true)) {
                            Array.get(0).set(1, true);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2) - (i*100)) && carte.get(panneau).get(1).equals(element.get(1)) && carte.get(panneau).get(0).equals(0)) {
                        if (Array.get(1).get(0).equals(true)) {
                            Array.get(1).set(1, true);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2)) && carte.get(panneau).get(1).equals(element.get(1) + (i*100)) && carte.get(panneau).get(0).equals(0)) {
                        if (Array.get(2).get(0).equals(true)) {
                            Array.get(2).set(1, true);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2)) && carte.get(panneau).get(1).equals(element.get(1) - (i*100)) && carte.get(panneau).get(0).equals(0)) {
                        if (Array.get(3).get(0).equals(true)) {
                            Array.get(3).set(1, true);
                            elementsConcernes.add(nombre);
                        }
                    }
                    nombre++;
                }
                for (int o = 0; o < Array.size(); o++) {
                    if (Array.get(o).get(1).equals(false)) {
                        Array.get(o).set(0, false);
                    }
                }
            }
        } else if (element.get(0) == 21) {
            int nombre = 0;
            for (JPanel panneau : carte.keySet()) {
                if (carte.get(panneau).get(2).equals(element.get(2) - 200) && carte.get(panneau).get(1).equals(element.get(1) - 100) && carte.get(panneau).get(0).equals(0)) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) - 200) && carte.get(panneau).get(1).equals(element.get(1) + 100) && carte.get(panneau).get(0).equals(0)) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) - 100) && carte.get(panneau).get(1).equals(element.get(1) + 200) && carte.get(panneau).get(0).equals(0)) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) + 100) && carte.get(panneau).get(1).equals(element.get(1) + 200) && carte.get(panneau).get(0).equals(0)) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) + 100) && carte.get(panneau).get(1).equals(element.get(1) - 200) && carte.get(panneau).get(0).equals(0)) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) - 100) && carte.get(panneau).get(1).equals(element.get(1) - 200) && carte.get(panneau).get(0).equals(0)) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) + 200) && carte.get(panneau).get(1).equals(element.get(1) + 100) && carte.get(panneau).get(0).equals(0)) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) + 200) && carte.get(panneau).get(1).equals(element.get(1) - 100) && carte.get(panneau).get(0).equals(0)) {
                    elementsConcernes.add(nombre);
                }
                nombre++;
            }
        } else if (element.get(0) == 22) {
            List<List<Boolean>> Array = new ArrayList<>();
            Array.add(new ArrayList<>(Arrays.asList(true, true)));
            Array.add(new ArrayList<>(Arrays.asList(true, true)));
            Array.add(new ArrayList<>(Arrays.asList(true, true)));
            Array.add(new ArrayList<>(Arrays.asList(true, true)));
            for (int i = 1; i < 9; i++) {
                int nombre = 0;
                Array.get(0).set(1, false);
                Array.get(1).set(1, false);
                Array.get(2).set(1, false);
                Array.get(3).set(1, false);
                for (JPanel panneau : carte.keySet()) {
                    if (carte.get(panneau).get(2).equals(element.get(2) + (i*100)) && carte.get(panneau).get(1).equals(element.get(1) + (i*100)) && carte.get(panneau).get(0).equals(0)) {
                        if (Array.get(0).get(0).equals(true)) {
                            Array.get(0).set(1, true);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2) - (i*100)) && carte.get(panneau).get(1).equals(element.get(1) - (i*100)) && carte.get(panneau).get(0).equals(0)) {
                        if (Array.get(1).get(0).equals(true)) {
                            Array.get(1).set(1, true);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2) - (i*100)) && carte.get(panneau).get(1).equals(element.get(1) + (i*100)) && carte.get(panneau).get(0).equals(0)) {
                        if (Array.get(2).get(0).equals(true)) {
                            Array.get(2).set(1, true);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2) + (i*100)) && carte.get(panneau).get(1).equals(element.get(1) - (i*100)) && carte.get(panneau).get(0).equals(0)) {
                        if (Array.get(3).get(0).equals(true)) {
                            Array.get(3).set(1, true);
                            elementsConcernes.add(nombre);
                        }
                    } 
                    nombre++;
                }
                for (int o = 0; o < Array.size(); o++) {
                    if (Array.get(o).get(1).equals(false)) {
                        Array.get(o).set(0, false);
                    }
                }
            }
        } else if (element.get(0) == 23) {
            List<List<Boolean>> Array = new ArrayList<>();
            Array.add(new ArrayList<>(Arrays.asList(true, true)));
            Array.add(new ArrayList<>(Arrays.asList(true, true)));
            Array.add(new ArrayList<>(Arrays.asList(true, true)));
            Array.add(new ArrayList<>(Arrays.asList(true, true)));
            Array.add(new ArrayList<>(Arrays.asList(true, true)));
            Array.add(new ArrayList<>(Arrays.asList(true, true)));
            Array.add(new ArrayList<>(Arrays.asList(true, true)));
            Array.add(new ArrayList<>(Arrays.asList(true, true)));
            for (int i = 1; i < 9; i++) {
                int nombre = 0;
                Array.get(0).set(1, false);
                Array.get(1).set(1, false);
                Array.get(2).set(1, false);
                Array.get(3).set(1, false);
                Array.get(4).set(1, false);
                Array.get(5).set(1, false);
                Array.get(6).set(1, false);
                Array.get(7).set(1, false);
                for (JPanel panneau : carte.keySet()) {
                    if (carte.get(panneau).get(2).equals(element.get(2) + (i*100)) && carte.get(panneau).get(1).equals(element.get(1)) && carte.get(panneau).get(0).equals(0)) {
                        if (Array.get(0).get(0).equals(true)) {
                            Array.get(0).set(1, true);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2) - (i*100)) && carte.get(panneau).get(1).equals(element.get(1)) && carte.get(panneau).get(0).equals(0)) {
                        if (Array.get(1).get(0).equals(true)) {
                            Array.get(1).set(1, true);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2)) && carte.get(panneau).get(1).equals(element.get(1) + (i*100)) && carte.get(panneau).get(0).equals(0)) {
                        if (Array.get(2).get(0).equals(true)) {
                            Array.get(2).set(1, true);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2)) && carte.get(panneau).get(1).equals(element.get(1) - (i*100)) && carte.get(panneau).get(0).equals(0)) {
                        if (Array.get(3).get(0).equals(true)) {
                            Array.get(3).set(1, true);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2) + (i*100)) && carte.get(panneau).get(1).equals(element.get(1) + (i*100)) && carte.get(panneau).get(0).equals(0)) {
                        if (Array.get(4).get(0).equals(true)) {
                            Array.get(4).set(1, true);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2) - (i*100)) && carte.get(panneau).get(1).equals(element.get(1) - (i*100)) && carte.get(panneau).get(0).equals(0)) {
                        if (Array.get(5).get(0).equals(true)) {
                            Array.get(5).set(1, true);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2) - (i*100)) && carte.get(panneau).get(1).equals(element.get(1) + (i*100)) && carte.get(panneau).get(0).equals(0)){
                        if (Array.get(6).get(0).equals(true)) {
                            Array.get(6).set(1, true);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2) + (i*100)) && carte.get(panneau).get(1).equals(element.get(1) - (i*100)) && carte.get(panneau).get(0).equals(0)) {
                        if (Array.get(7).get(0).equals(true)) {
                            Array.get(7).set(1, true);
                            elementsConcernes.add(nombre);
                        }
                    } 
                    nombre++;
                }
                for (int o = 0; o < Array.size(); o++) {
                    if (Array.get(o).get(1).equals(false)) {
                        Array.get(o).set(0, false);
                    }
                }
            }
        } else if (element.get(0) == 25) {
            int nombre = 0;
            for (JPanel panneau : carte.keySet()) {
                if (carte.get(panneau).get(2).equals(element.get(2) - 100) && carte.get(panneau).get(1).equals(element.get(1)) && carte.get(panneau).get(0).equals(0)) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) + 100) && carte.get(panneau).get(1).equals(element.get(1)) && carte.get(panneau).get(0).equals(0)) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) - 100) && carte.get(panneau).get(1).equals(element.get(1) + 100) && carte.get(panneau).get(0).equals(0)) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) - 100) && carte.get(panneau).get(1).equals(element.get(1) - 100) && carte.get(panneau).get(0).equals(0)) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) + 100) && carte.get(panneau).get(1).equals(element.get(1) + 100) && carte.get(panneau).get(0).equals(0)) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) + 100) && carte.get(panneau).get(1).equals(element.get(1) - 100) && carte.get(panneau).get(0).equals(0)) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2)) && carte.get(panneau).get(1).equals(element.get(1) + 100) && carte.get(panneau).get(0).equals(0)) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2)) && carte.get(panneau).get(1).equals(element.get(1) - 100) && carte.get(panneau).get(0).equals(0)) {
                    elementsConcernes.add(nombre);
                } 
                nombre += 1;
            }
        }
        return elementsConcernes;
    }
    public List<Integer> calculeConcernedCasesBlack(Map<JPanel, List<Integer>> carte, List<Integer> element) {
        List<Integer> elementsConcernes = new ArrayList<>();
        if (element.get(0) == 14) {
            int nombre = 0;
            for (JPanel panneau : carte.keySet()) {
                if (carte.get(panneau).get(2).equals(element.get(2) + 100) && carte.get(panneau).get(1).equals(element.get(1)) && carte.get(panneau).get(0).equals(0)) {
                    elementsConcernes.add(nombre);
                    if (element.get(2) == 200) {
                        int n = 0;
                        for (JPanel thing : carte.keySet()) {
                            if (carte.get(thing).get(2).equals(element.get(2) + 200) && carte.get(thing).get(1).equals(element.get(1)) && carte.get(thing).get(0).equals(0)) {
                                elementsConcernes.add(n);
                                break;
                            }
                            n++;
                        }
                    }
                    break;
                } 
                nombre += 1;
            }
        }  else if (element.get(0) == 11) {
            int nombre = 0;
            for (JPanel panneau : carte.keySet()) {
                if (carte.get(panneau).get(2).equals(element.get(2) - 200) && carte.get(panneau).get(1).equals(element.get(1) - 100) && carte.get(panneau).get(0).equals(0)) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) - 200) && carte.get(panneau).get(1).equals(element.get(1) + 100) && carte.get(panneau).get(0).equals(0)) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) - 100) && carte.get(panneau).get(1).equals(element.get(1) + 200) && carte.get(panneau).get(0).equals(0)) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) + 100) && carte.get(panneau).get(1).equals(element.get(1) + 200) && carte.get(panneau).get(0).equals(0)) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) + 100) && carte.get(panneau).get(1).equals(element.get(1) - 200) && carte.get(panneau).get(0).equals(0)) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) - 100) && carte.get(panneau).get(1).equals(element.get(1) - 200) && carte.get(panneau).get(0).equals(0)) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) + 200) && carte.get(panneau).get(1).equals(element.get(1) + 100) && carte.get(panneau).get(0).equals(0)) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) + 200) && carte.get(panneau).get(1).equals(element.get(1) - 100) && carte.get(panneau).get(0).equals(0)) {
                    elementsConcernes.add(nombre);
                }
                nombre++;
            }
        } else if (element.get(0) == 10) {
            List<List<Boolean>> Array = new ArrayList<>();
            Array.add(new ArrayList<>(Arrays.asList(true, true)));
            Array.add(new ArrayList<>(Arrays.asList(true, true)));
            Array.add(new ArrayList<>(Arrays.asList(true, true)));
            Array.add(new ArrayList<>(Arrays.asList(true, true)));
            for (int i = 1; i < 9; i++) {
                int nombre = 0;
                Array.get(0).set(1, false);
                Array.get(1).set(1, false);
                Array.get(2).set(1, false);
                Array.get(3).set(1, false);
                for (JPanel panneau : carte.keySet()) {
                    if (carte.get(panneau).get(2).equals(element.get(2) + (i*100)) && carte.get(panneau).get(1).equals(element.get(1)) && carte.get(panneau).get(0).equals(0)) {
                        if (Array.get(0).get(0).equals(true)) {
                            Array.get(0).set(1, true);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2) - (i*100)) && carte.get(panneau).get(1).equals(element.get(1)) && carte.get(panneau).get(0).equals(0)) {
                        if (Array.get(1).get(0).equals(true)) {
                            Array.get(1).set(1, true);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2)) && carte.get(panneau).get(1).equals(element.get(1) + (i*100)) && carte.get(panneau).get(0).equals(0)) {
                        if (Array.get(2).get(0).equals(true)) {
                            Array.get(2).set(1, true);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2)) && carte.get(panneau).get(1).equals(element.get(1) - (i*100)) && carte.get(panneau).get(0).equals(0)) {
                        if (Array.get(3).get(0).equals(true)) {
                            Array.get(3).set(1, true);
                            elementsConcernes.add(nombre);
                        }
                    }
                    nombre++;
                }
                for (int o = 0; o < Array.size(); o++) {
                    if (Array.get(o).get(1).equals(false)) {
                        Array.get(o).set(0, false);
                    }
                }
            }
        } else if (element.get(0) == 12) {
            List<List<Boolean>> Array = new ArrayList<>();
            Array.add(new ArrayList<>(Arrays.asList(true, true)));
            Array.add(new ArrayList<>(Arrays.asList(true, true)));
            Array.add(new ArrayList<>(Arrays.asList(true, true)));
            Array.add(new ArrayList<>(Arrays.asList(true, true)));
            for (int i = 1; i < 9; i++) {
                int nombre = 0;
                Array.get(0).set(1, false);
                Array.get(1).set(1, false);
                Array.get(2).set(1, false);
                Array.get(3).set(1, false);
                for (JPanel panneau : carte.keySet()) {
                    if (carte.get(panneau).get(2).equals(element.get(2) + (i*100)) && carte.get(panneau).get(1).equals(element.get(1) + (i*100)) && carte.get(panneau).get(0).equals(0)) {
                        if (Array.get(0).get(0).equals(true)) {
                            Array.get(0).set(1, true);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2) - (i*100)) && carte.get(panneau).get(1).equals(element.get(1) - (i*100)) && carte.get(panneau).get(0).equals(0)) {
                        if (Array.get(1).get(0).equals(true)) {
                            Array.get(1).set(1, true);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2) - (i*100)) && carte.get(panneau).get(1).equals(element.get(1) + (i*100)) && carte.get(panneau).get(0).equals(0)) {
                        if (Array.get(2).get(0).equals(true)) {
                            Array.get(2).set(1, true);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2) + (i*100)) && carte.get(panneau).get(1).equals(element.get(1) - (i*100)) && carte.get(panneau).get(0).equals(0)) {
                        if (Array.get(3).get(0).equals(true)) {
                            Array.get(3).set(1, true);
                            elementsConcernes.add(nombre);
                        }
                    } 
                    nombre++;
                }
                for (int o = 0; o < Array.size(); o++) {
                    if (Array.get(o).get(1).equals(false)) {
                        Array.get(o).set(0, false);
                    }
                }
            }
        } else if (element.get(0) == 23) {
            List<List<Boolean>> Array = new ArrayList<>();
            Array.add(new ArrayList<>(Arrays.asList(true, true)));
            Array.add(new ArrayList<>(Arrays.asList(true, true)));
            Array.add(new ArrayList<>(Arrays.asList(true, true)));
            Array.add(new ArrayList<>(Arrays.asList(true, true)));
            Array.add(new ArrayList<>(Arrays.asList(true, true)));
            Array.add(new ArrayList<>(Arrays.asList(true, true)));
            Array.add(new ArrayList<>(Arrays.asList(true, true)));
            Array.add(new ArrayList<>(Arrays.asList(true, true)));
            for (int i = 1; i < 9; i++) {
                int nombre = 0;
                Array.get(0).set(1, false);
                Array.get(1).set(1, false);
                Array.get(2).set(1, false);
                Array.get(3).set(1, false);
                Array.get(4).set(1, false);
                Array.get(5).set(1, false);
                Array.get(6).set(1, false);
                Array.get(7).set(1, false);
                for (JPanel panneau : carte.keySet()) {
                    if (carte.get(panneau).get(2).equals(element.get(2) + (i*100)) && carte.get(panneau).get(1).equals(element.get(1)) && carte.get(panneau).get(0).equals(0)) {
                        if (Array.get(0).get(0).equals(true)) {
                            Array.get(0).set(1, true);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2) - (i*100)) && carte.get(panneau).get(1).equals(element.get(1)) && carte.get(panneau).get(0).equals(0)) {
                        if (Array.get(1).get(0).equals(true)) {
                            Array.get(1).set(1, true);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2)) && carte.get(panneau).get(1).equals(element.get(1) + (i*100)) && carte.get(panneau).get(0).equals(0)) {
                        if (Array.get(2).get(0).equals(true)) {
                            Array.get(2).set(1, true);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2)) && carte.get(panneau).get(1).equals(element.get(1) - (i*100)) && carte.get(panneau).get(0).equals(0)) {
                        if (Array.get(3).get(0).equals(true)) {
                            Array.get(3).set(1, true);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2) + (i*100)) && carte.get(panneau).get(1).equals(element.get(1) + (i*100)) && carte.get(panneau).get(0).equals(0)) {
                        if (Array.get(4).get(0).equals(true)) {
                            Array.get(4).set(1, true);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2) - (i*100)) && carte.get(panneau).get(1).equals(element.get(1) - (i*100)) && carte.get(panneau).get(0).equals(0)) {
                        if (Array.get(5).get(0).equals(true)) {
                            Array.get(5).set(1, true);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2) - (i*100)) && carte.get(panneau).get(1).equals(element.get(1) + (i*100)) && carte.get(panneau).get(0).equals(0)){
                        if (Array.get(6).get(0).equals(true)) {
                            Array.get(6).set(1, true);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2) + (i*100)) && carte.get(panneau).get(1).equals(element.get(1) - (i*100)) && carte.get(panneau).get(0).equals(0)) {
                        if (Array.get(7).get(0).equals(true)) {
                            Array.get(7).set(1, true);
                            elementsConcernes.add(nombre);
                        }
                    } 
                    nombre++;
                }
                for (int o = 0; o < Array.size(); o++) {
                    if (Array.get(o).get(1).equals(false)) {
                        Array.get(o).set(0, false);
                    }
                }
            }
        } else if (element.get(0) == 15) {
            int nombre = 0;
            for (JPanel panneau : carte.keySet()) {
                if (carte.get(panneau).get(2).equals(element.get(2) - 100) && carte.get(panneau).get(1).equals(element.get(1)) && carte.get(panneau).get(0).equals(0)) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) + 100) && carte.get(panneau).get(1).equals(element.get(1)) && carte.get(panneau).get(0).equals(0)) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) - 100) && carte.get(panneau).get(1).equals(element.get(1) + 100) && carte.get(panneau).get(0).equals(0)) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) - 100) && carte.get(panneau).get(1).equals(element.get(1) - 100) && carte.get(panneau).get(0).equals(0)) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) + 100) && carte.get(panneau).get(1).equals(element.get(1) + 100) && carte.get(panneau).get(0).equals(0)) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) + 100) && carte.get(panneau).get(1).equals(element.get(1) - 100) && carte.get(panneau).get(0).equals(0)) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2)) && carte.get(panneau).get(1).equals(element.get(1) + 100) && carte.get(panneau).get(0).equals(0)) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2)) && carte.get(panneau).get(1).equals(element.get(1) - 100) && carte.get(panneau).get(0).equals(0)) {
                    elementsConcernes.add(nombre);
                } 
                nombre += 1;
            }
        }
        return elementsConcernes;
    }
    public List<Integer> calculeInDangerCasesBlack(Map<JPanel, List<Integer>> carte, List<Integer> element, List<Integer> constante) {
        List<Integer> elementsConcernes = new ArrayList<>();
        if (element.get(0) == 14) {
            int nombre = 0;
            for (JPanel thing : carte.keySet()) {
                if ((carte.get(thing).get(0) == 20 || constante.get(1) == carte.get(thing).get(0) || carte.get(thing).get(0) == 24 || carte.get(thing).get(0) == 23 || carte.get(thing).get(0) == 22 || carte.get(thing).get(0) == 21) && carte.get(thing).get(2) == element.get(2) + 100 && carte.get(thing).get(1) == element.get(1) - 100) {
                    elementsConcernes.add(nombre);
                } else if ((carte.get(thing).get(0) == 20 || constante.get(1) == carte.get(thing).get(0) || carte.get(thing).get(0) == 24 || carte.get(thing).get(0) == 23 || carte.get(thing).get(0) == 22 || carte.get(thing).get(0) == 21) && carte.get(thing).get(2) == element.get(2) + 100 && carte.get(thing).get(1) == element.get(1) + 100) {
                    elementsConcernes.add(nombre);
                } 
                nombre += 1;
            }
        }
        else if (element.get(0) == 11) {
            int nombre = 0;
            for (JPanel panneau : carte.keySet()) {
                if (carte.get(panneau).get(2).equals(element.get(2) - 200) && carte.get(panneau).get(1).equals(element.get(1) - 100) && (carte.get(panneau).get(0) == 20 || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || constante.get(1) == carte.get(panneau).get(0) || carte.get(panneau).get(0) == 21)) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) - 200) && carte.get(panneau).get(1).equals(element.get(1) + 100) && (carte.get(panneau).get(0) == 20 || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || constante.get(1) == carte.get(panneau).get(0) || carte.get(panneau).get(0) == 21)) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) - 100) && carte.get(panneau).get(1).equals(element.get(1) + 200) && (carte.get(panneau).get(0) == 20 || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22  || constante.get(1) == carte.get(panneau).get(0) || carte.get(panneau).get(0) == 21)) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) + 100) && carte.get(panneau).get(1).equals(element.get(1) + 200) && (carte.get(panneau).get(0) == 20 || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22  || constante.get(1) == carte.get(panneau).get(0) || carte.get(panneau).get(0) == 21)) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) + 100) && carte.get(panneau).get(1).equals(element.get(1) - 200) && (carte.get(panneau).get(0) == 20 || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22  || constante.get(1) == carte.get(panneau).get(0) || carte.get(panneau).get(0) == 21)) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) - 100) && carte.get(panneau).get(1).equals(element.get(1) - 200) && (carte.get(panneau).get(0) == 20 || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22  || constante.get(1) == carte.get(panneau).get(0) || carte.get(panneau).get(0) == 21)) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) + 200) && carte.get(panneau).get(1).equals(element.get(1) + 100) && (carte.get(panneau).get(0) == 20 || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22  || constante.get(1) == carte.get(panneau).get(0) || carte.get(panneau).get(0) == 21)) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) + 200) && carte.get(panneau).get(1).equals(element.get(1) - 100) && (carte.get(panneau).get(0) == 20 || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22  || constante.get(1) == carte.get(panneau).get(0) || carte.get(panneau).get(0) == 21)) {
                    elementsConcernes.add(nombre);
                }
                nombre++;
            }
        } else if (element.get(0) == 10) {
            List<List<Boolean>> array = new ArrayList<>(); 
            array.add(new ArrayList<>(Arrays.asList(true, true)));
            array.add(new ArrayList<>(Arrays.asList(true, true)));
            array.add(new ArrayList<>(Arrays.asList(true, true)));
            array.add(new ArrayList<>(Arrays.asList(true, true)));
            for (int i = 1; i < 9; i++) {
                int nombre = 0;  
                for (JPanel panneau : carte.keySet()) {
                    if (carte.get(panneau).get(2).equals(element.get(2) + (i*100)) && carte.get(panneau).get(1).equals(element.get(1)) && (carte.get(panneau).get(0) == 15  || constante.get(0) == carte.get(panneau).get(0) || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == 10)) {
                        array.get(0).set(0, false);
                        array.get(0).set(1, false);
                    } else if (carte.get(panneau).get(2).equals(element.get(2) - (i*100)) && carte.get(panneau).get(1).equals(element.get(1)) && (constante.get(0) == carte.get(panneau).get(0) || carte.get(panneau).get(0) == 15 || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == 10)) {
                        array.get(1).set(0, false);
                        array.get(1).set(1, false);
                    } else if (carte.get(panneau).get(2).equals(element.get(2)) && carte.get(panneau).get(1).equals(element.get(1) + (i*100)) && (constante.get(0) == carte.get(panneau).get(0) || carte.get(panneau).get(0) == 15 || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == 10)) {
                        array.get(2).set(0, false);
                        array.get(2).set(1, false);
                    } else if (carte.get(panneau).get(2).equals(element.get(2)) && carte.get(panneau).get(1).equals(element.get(1) - (i*100)) && (constante.get(0) == carte.get(panneau).get(0) || carte.get(panneau).get(0) == 15 || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == 10)) {
                        array.get(3).set(0, false);
                        array.get(3).set(1, false);
                    }
                }
                for (JPanel panneau : carte.keySet()) {
                    if (carte.get(panneau).get(2).equals(element.get(2) + (i*100)) && carte.get(panneau).get(1).equals(element.get(1)) && (carte.get(panneau).get(0) == 20 || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || constante.get(1) == carte.get(panneau).get(0))) {
                        if (array.get(0).get(0).equals(true)) {
                            array.get(0).set(1, false);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2) - (i*100)) && carte.get(panneau).get(1).equals(element.get(1)) && (carte.get(panneau).get(0) == 20 || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || constante.get(1) == carte.get(panneau).get(0))) {
                        if (array.get(1).get(0).equals(true)) {
                            array.get(1).set(1, false);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2)) && carte.get(panneau).get(1).equals(element.get(1) + (i*100)) && (carte.get(panneau).get(0) == 20 || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || constante.get(1) == carte.get(panneau).get(0))) {
                        if (array.get(2).get(0).equals(true)) {
                            array.get(2).set(1, false);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2)) && carte.get(panneau).get(1).equals(element.get(1) - (i*100)) && (carte.get(panneau).get(0) == 20 || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || constante.get(1) == carte.get(panneau).get(0))) {
                        if (array.get(3).get(0).equals(true)) {
                            array.get(3).set(1, false);
                            elementsConcernes.add(nombre);
                        }
                    } 
                    nombre++;
                }
                for (int o = 0; o < array.size(); o++) {
                    if (array.get(o).get(1).equals(false)) {
                        array.get(o).set(0, false);
                    }
                }
            }
        } else if (element.get(0) == 12) {
            List<List<Boolean>> array = new ArrayList<>(); 
            array.add(new ArrayList<>(Arrays.asList(true, true)));
            array.add(new ArrayList<>(Arrays.asList(true, true)));
            array.add(new ArrayList<>(Arrays.asList(true, true)));
            array.add(new ArrayList<>(Arrays.asList(true, true)));
            for (int i = 1; i < 9; i++) {
                int nombre = 0;  
                for (JPanel panneau : carte.keySet()) {
                    if (carte.get(panneau).get(2).equals(element.get(2) + (i*100)) && carte.get(panneau).get(1).equals(element.get(1) + (i*100)) && (constante.get(0) == carte.get(panneau).get(0) || carte.get(panneau).get(0) == 15  || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == 10)) {
                        array.get(0).set(0, false);
                        array.get(0).set(1, false);
                    } else if (carte.get(panneau).get(2).equals(element.get(2) - (i*100)) && carte.get(panneau).get(1).equals(element.get(1) - (i*100)) && (constante.get(0) == carte.get(panneau).get(0) || carte.get(panneau).get(0) == 15  || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == 10)) {
                        array.get(1).set(0, false);
                        array.get(1).set(1, false);
                    } else if (carte.get(panneau).get(2).equals(element.get(2) - (i*100)) && carte.get(panneau).get(1).equals(element.get(1) + (i*100)) && (constante.get(0) == carte.get(panneau).get(0) || carte.get(panneau).get(0) == 15  || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == 10)) {
                        array.get(2).set(0, false);
                        array.get(2).set(1, false);
                    } else if (carte.get(panneau).get(2).equals(element.get(2) + (i*100)) && carte.get(panneau).get(1).equals(element.get(1) - (i*100)) && (constante.get(0) == carte.get(panneau).get(0) || carte.get(panneau).get(0) == 15  || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == 10)) {
                        array.get(3).set(0, false);
                        array.get(3).set(1, false);
                    }
                }
                for (JPanel panneau : carte.keySet()) {
                    if (carte.get(panneau).get(2).equals(element.get(2) + (i*100)) && carte.get(panneau).get(1).equals(element.get(1) + (i*100)) && (carte.get(panneau).get(0) == 20 || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || constante.get(1) == carte.get(panneau).get(0))) {
                        if (array.get(0).get(0).equals(true)) {
                            array.get(0).set(1, false);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2) - (i*100)) && carte.get(panneau).get(1).equals(element.get(1) - (i*100)) && (carte.get(panneau).get(0) == 20 || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || constante.get(1) == carte.get(panneau).get(0))) {
                        if (array.get(1).get(0).equals(true)) {
                            array.get(1).set(1, false);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2) - (i*100)) && carte.get(panneau).get(1).equals(element.get(1) + (i*100)) && (carte.get(panneau).get(0) == 20 || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || constante.get(1) == carte.get(panneau).get(0))) {
                        if (array.get(2).get(0).equals(true)) {
                            array.get(2).set(1, false);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2) + (i*100)) && carte.get(panneau).get(1).equals(element.get(1) - (i*100)) && (carte.get(panneau).get(0) == 20 || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || constante.get(1) == carte.get(panneau).get(0))) {
                        if (array.get(3).get(0).equals(true)) {
                            array.get(3).set(1, false);
                            elementsConcernes.add(nombre);
                        }
                    } 
                    nombre++;
                }
                for (int o = 0; o < array.size(); o++) {
                    if (array.get(o).get(1).equals(false)) {
                        array.get(o).set(0, false);
                    }
                }
            }
        } else if (element.get(0) == 13) {
            List<List<Boolean>> array = new ArrayList<>(); 
            array.add(new ArrayList<>(Arrays.asList(true, true)));
            array.add(new ArrayList<>(Arrays.asList(true, true)));
            array.add(new ArrayList<>(Arrays.asList(true, true)));
            array.add(new ArrayList<>(Arrays.asList(true, true)));
            array.add(new ArrayList<>(Arrays.asList(true, true)));
            array.add(new ArrayList<>(Arrays.asList(true, true)));
            array.add(new ArrayList<>(Arrays.asList(true, true)));
            array.add(new ArrayList<>(Arrays.asList(true, true)));
            for (int i = 1; i < 9; i++) {
                int nombre = 0;  
                for (JPanel panneau : carte.keySet()) {
                    if (carte.get(panneau).get(2).equals(element.get(2) + (i*100)) && carte.get(panneau).get(1).equals(element.get(1) + (i*100)) && (carte.get(panneau).get(0) == 15 || constante.get(0) == carte.get(panneau).get(0)  || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == 10)) {
                        array.get(0).set(0, false);
                        array.get(0).set(1, false);
                    } else if (carte.get(panneau).get(2).equals(element.get(2) - (i*100)) && carte.get(panneau).get(1).equals(element.get(1) - (i*100)) && (carte.get(panneau).get(0) == 15 || constante.get(0) == carte.get(panneau).get(0) || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == 10)) {
                        array.get(1).set(0, false);
                        array.get(1).set(1, false);
                    } else if (carte.get(panneau).get(2).equals(element.get(2) - (i*100)) && carte.get(panneau).get(1).equals(element.get(1) + (i*100)) && (carte.get(panneau).get(0) == 15 || constante.get(0) == carte.get(panneau).get(0)  || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == 10)) {
                        array.get(2).set(0, false);
                        array.get(2).set(1, false);
                    } else if (carte.get(panneau).get(2).equals(element.get(2) + (i*100)) && carte.get(panneau).get(1).equals(element.get(1) - (i*100)) && (carte.get(panneau).get(0) == 15 || constante.get(0) == carte.get(panneau).get(0)  || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == 10)) {
                        array.get(3).set(0, false);
                        array.get(3).set(1, false);
                    } else if (carte.get(panneau).get(2).equals(element.get(2) + (i*100)) && carte.get(panneau).get(1).equals(element.get(1)) && (carte.get(panneau).get(0) == 15 || constante.get(0) == carte.get(panneau).get(0)  || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == 10)) {
                        array.get(4).set(0, false);
                        array.get(4).set(1, false);
                    } else if (carte.get(panneau).get(2).equals(element.get(2) - (i*100)) && carte.get(panneau).get(1).equals(element.get(1)) && (carte.get(panneau).get(0) == 15 || constante.get(0) == carte.get(panneau).get(0)  || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == 10)) {
                        array.get(5).set(0, false);
                        array.get(5).set(1, false);
                    } else if (carte.get(panneau).get(2).equals(element.get(2)) && carte.get(panneau).get(1).equals(element.get(1) + (i*100)) && (carte.get(panneau).get(0) == 15 || constante.get(0) == carte.get(panneau).get(0)  || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == 10)) {
                        array.get(6).set(0, false);
                        array.get(6).set(1, false);
                    } else if (carte.get(panneau).get(2).equals(element.get(2)) && carte.get(panneau).get(1).equals(element.get(1) - (i*100)) && (carte.get(panneau).get(0) == 15 || constante.get(0) == carte.get(panneau).get(0) || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == 10)) {
                        array.get(7).set(0, false);
                        array.get(7).set(1, false);
                    }
                }
                for (JPanel panneau : carte.keySet()) {
                    if (carte.get(panneau).get(2).equals(element.get(2) + (i*100)) && carte.get(panneau).get(1).equals(element.get(1) + (i*100)) && (carte.get(panneau).get(0) == 20 || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || constante.get(1) == carte.get(panneau).get(0))) {
                        if (array.get(0).get(0).equals(true)) {
                            array.get(0).set(1, false);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2) - (i*100)) && carte.get(panneau).get(1).equals(element.get(1) - (i*100)) && (carte.get(panneau).get(0) == 20 || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || constante.get(1) == carte.get(panneau).get(0))) {
                        if (array.get(1).get(0).equals(true)) {
                            array.get(1).set(1, false);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2) - (i*100)) && carte.get(panneau).get(1).equals(element.get(1) + (i*100)) && (carte.get(panneau).get(0) == 20 || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || constante.get(1) == carte.get(panneau).get(0))) {
                        if (array.get(2).get(0).equals(true)) {
                            array.get(2).set(1, false);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2) + (i*100)) && carte.get(panneau).get(1).equals(element.get(1) - (i*100)) && (carte.get(panneau).get(0) == 20 || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || constante.get(1) == carte.get(panneau).get(0))) {
                        if (array.get(3).get(0).equals(true)) {
                            array.get(3).set(1, false);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2) + (i*100)) && carte.get(panneau).get(1).equals(element.get(1)) && (carte.get(panneau).get(0) == 20 || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || constante.get(1) == carte.get(panneau).get(0))) {
                        if (array.get(4).get(0).equals(true)) {
                            array.get(4).set(1, false);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2) - (i*100)) && carte.get(panneau).get(1).equals(element.get(1)) && (carte.get(panneau).get(0) == 20 || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21|| constante.get(1) == carte.get(panneau).get(0) )) {
                        if (array.get(5).get(0).equals(true)) {
                            array.get(5).set(1, false);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2)) && carte.get(panneau).get(1).equals(element.get(1) + (i*100)) && (carte.get(panneau).get(0) == 20 || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || constante.get(1) == carte.get(panneau).get(0))) {
                        if (array.get(6).get(0).equals(true)) {
                            array.get(6).set(1, false);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2)) && carte.get(panneau).get(1).equals(element.get(1) - (i*100)) && (carte.get(panneau).get(0) == 20 || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || constante.get(1) == carte.get(panneau).get(0))) {
                        if (array.get(7).get(0).equals(true)) {
                            array.get(7).set(1, false);
                            elementsConcernes.add(nombre);
                        }
                    } 
                    nombre++;
                }
                for (int o = 0; o < array.size(); o++) {
                    if (array.get(o).get(1).equals(false)) {
                        array.get(o).set(0, false);
                    }
                }
            }
        } else if (element.get(0) == 15) {
            int nombre = 0;
            for (JPanel panneau : carte.keySet()) {
                if (carte.get(panneau).get(2).equals(element.get(2) - 100) && carte.get(panneau).get(1).equals(element.get(1)) && (carte.get(panneau).get(0) == 20 || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || constante.get(1) == carte.get(panneau).get(0))) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) + 100) && carte.get(panneau).get(1).equals(element.get(1)) && (carte.get(panneau).get(0) == 20 || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || constante.get(1) == carte.get(panneau).get(0))) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) - 100) && carte.get(panneau).get(1).equals(element.get(1) + 100) && (carte.get(panneau).get(0) == 20 || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || constante.get(1) == carte.get(panneau).get(0))) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) - 100) && carte.get(panneau).get(1).equals(element.get(1) - 100) && (carte.get(panneau).get(0) == 20 || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || constante.get(1) == carte.get(panneau).get(0))) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) + 100) && carte.get(panneau).get(1).equals(element.get(1) + 100) && (carte.get(panneau).get(0) == 20 || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || constante.get(1) == carte.get(panneau).get(0))) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) + 100) && carte.get(panneau).get(1).equals(element.get(1) - 100) && (carte.get(panneau).get(0) == 20 || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || constante.get(1) == carte.get(panneau).get(0))) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2)) && carte.get(panneau).get(1).equals(element.get(1) + 100) && (carte.get(panneau).get(0) == 20 || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || constante.get(1) == carte.get(panneau).get(0))) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2)) && carte.get(panneau).get(1).equals(element.get(1) - 100) && (carte.get(panneau).get(0) == 20 || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || constante.get(1) == carte.get(panneau).get(0))) {
                    elementsConcernes.add(nombre);
                } 
                nombre += 1;
            }
        }
        return elementsConcernes;
    }
    public List<Integer> calculeInDangerCases(Map<JPanel, List<Integer>> carte, List<Integer> element, List<Integer> constante) {
        List<Integer> elementsConcernes = new ArrayList<>();
        if (element.get(0) == 24) {
            int nombre = 0;
            for (JPanel thing : carte.keySet()) {
                if ((carte.get(thing).get(0) == constante.get(1) || carte.get(thing).get(0) == 10 || carte.get(thing).get(0) == 14 || carte.get(thing).get(0) == 13 || carte.get(thing).get(0) == 12 || carte.get(thing).get(0) == 11) && carte.get(thing).get(2) == element.get(2) - 100 && carte.get(thing).get(1) == element.get(1) - 100) {
                    elementsConcernes.add(nombre);
                } else if (( carte.get(thing).get(0) == constante.get(1) || carte.get(thing).get(0) == 10 || carte.get(thing).get(0) == 14 || carte.get(thing).get(0) == 13 || carte.get(thing).get(0) == 12 || carte.get(thing).get(0) == 11) && carte.get(thing).get(2) == element.get(2) - 100 && carte.get(thing).get(1) == element.get(1) + 100) {
                    elementsConcernes.add(nombre);
                } 
                nombre += 1;
            }
        }
        else if (element.get(0) == 21) {
            int nombre = 0;
            for (JPanel panneau : carte.keySet()) {
                if (carte.get(panneau).get(2).equals(element.get(2) - 200) && carte.get(panneau).get(1).equals(element.get(1) - 100) && (carte.get(panneau).get(0) == 10 || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == constante.get(1))) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) - 200) && carte.get(panneau).get(1).equals(element.get(1) + 100) && (carte.get(panneau).get(0) == 10 || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11|| carte.get(panneau).get(0) == constante.get(1))) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) - 100) && carte.get(panneau).get(1).equals(element.get(1) + 200) && (carte.get(panneau).get(0) == 10 || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == constante.get(1))) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) + 100) && carte.get(panneau).get(1).equals(element.get(1) + 200) && (carte.get(panneau).get(0) == 10 || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == constante.get(1))) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) + 100) && carte.get(panneau).get(1).equals(element.get(1) - 200) && (carte.get(panneau).get(0) == 10 || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == constante.get(1))) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) - 100) && carte.get(panneau).get(1).equals(element.get(1) - 200) && (carte.get(panneau).get(0) == 10 || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == constante.get(1))) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) + 200) && carte.get(panneau).get(1).equals(element.get(1) + 100) && (carte.get(panneau).get(0) == 10 || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == constante.get(1))) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) + 200) && carte.get(panneau).get(1).equals(element.get(1) - 100) && (carte.get(panneau).get(0) == 10 || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11|| carte.get(panneau).get(0) == constante.get(1) )) {
                    elementsConcernes.add(nombre);
                }
                nombre++;
            }
        } else if (element.get(0) == 20) {
            List<List<Boolean>> array = new ArrayList<>(); 
            array.add(new ArrayList<>(Arrays.asList(true, true)));
            array.add(new ArrayList<>(Arrays.asList(true, true)));
            array.add(new ArrayList<>(Arrays.asList(true, true)));
            array.add(new ArrayList<>(Arrays.asList(true, true)));
            for (int i = 1; i < 9; i++) {
                int nombre = 0;  
                for (JPanel panneau : carte.keySet()) {
                    if (carte.get(panneau).get(2).equals(element.get(2) + (i*100)) && carte.get(panneau).get(1).equals(element.get(1)) && (carte.get(panneau).get(0) == 25 || carte.get(panneau).get(0) == constante.get(0)  || carte.get(panneau).get(1) == 24|| carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || carte.get(panneau).get(0) == 20)) {
                        array.get(0).set(0, false);
                        array.get(0).set(1, false);
                    } else if (carte.get(panneau).get(2).equals(element.get(2) - (i*100)) && carte.get(panneau).get(1).equals(element.get(1)) && (carte.get(panneau).get(0) == 25 || carte.get(panneau).get(0) == constante.get(0) || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || carte.get(panneau).get(0) == 20)) {
                        array.get(1).set(0, false);
                        array.get(1).set(1, false);
                    } else if (carte.get(panneau).get(2).equals(element.get(2)) && carte.get(panneau).get(1).equals(element.get(1) + (i*100)) && (carte.get(panneau).get(0) == 25 || carte.get(panneau).get(0) == constante.get(0) || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || carte.get(panneau).get(0) == 20)) {
                        array.get(2).set(0, false);
                        array.get(2).set(1, false);
                    } else if (carte.get(panneau).get(2).equals(element.get(2)) && carte.get(panneau).get(1).equals(element.get(1) - (i*100)) && (carte.get(panneau).get(0) == 25 || carte.get(panneau).get(0) == constante.get(0) || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || carte.get(panneau).get(0) == 20)) {
                        array.get(3).set(0, false);
                        array.get(3).set(1, false);
                    }
                }
                for (JPanel panneau : carte.keySet()) {
                    if (carte.get(panneau).get(2).equals(element.get(2) + (i*100)) && carte.get(panneau).get(1).equals(element.get(1)) && (carte.get(panneau).get(0) == 10 || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == constante.get(1))) {
                        if (array.get(0).get(0).equals(true)) {
                            array.get(0).set(1, false);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2) - (i*100)) && carte.get(panneau).get(1).equals(element.get(1)) && (carte.get(panneau).get(0) == 10 || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == constante.get(1))) {
                        if (array.get(1).get(0).equals(true)) {
                            array.get(1).set(1, false);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2)) && carte.get(panneau).get(1).equals(element.get(1) + (i*100)) && (carte.get(panneau).get(0) == 10 || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == constante.get(1))) {
                        if (array.get(2).get(0).equals(true)) {
                            array.get(2).set(1, false);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2)) && carte.get(panneau).get(1).equals(element.get(1) - (i*100)) && (carte.get(panneau).get(0) == 10 || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == constante.get(1))) {
                        if (array.get(3).get(0).equals(true)) {
                            array.get(3).set(1, false);
                            elementsConcernes.add(nombre);
                        }
                    } 
                    nombre++;
                }
                for (int o = 0; o < array.size(); o++) {
                    if (array.get(o).get(1).equals(false)) {
                        array.get(o).set(0, false);
                    }
                }
            }
        } else if (element.get(0) == 22) {
            List<List<Boolean>> array = new ArrayList<>(); 
            array.add(new ArrayList<>(Arrays.asList(true, true)));
            array.add(new ArrayList<>(Arrays.asList(true, true)));
            array.add(new ArrayList<>(Arrays.asList(true, true)));
            array.add(new ArrayList<>(Arrays.asList(true, true)));
            for (int i = 1; i < 9; i++) {
                int nombre = 0;  
                for (JPanel panneau : carte.keySet()) {
                    if (carte.get(panneau).get(2).equals(element.get(2) + (i*100)) && carte.get(panneau).get(1).equals(element.get(1) + (i*100)) && (carte.get(panneau).get(0) == 25 || carte.get(panneau).get(0) == constante.get(0)  || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || carte.get(panneau).get(0) == 20)) {
                        array.get(0).set(0, false);
                        array.get(0).set(1, false);
                    } else if (carte.get(panneau).get(2).equals(element.get(2) - (i*100)) && carte.get(panneau).get(1).equals(element.get(1) - (i*100)) && (carte.get(panneau).get(0) == 25 || carte.get(panneau).get(0) == constante.get(0) || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || carte.get(panneau).get(0) == 20)) {
                        array.get(1).set(0, false);
                        array.get(1).set(1, false);
                    } else if (carte.get(panneau).get(2).equals(element.get(2) - (i*100)) && carte.get(panneau).get(1).equals(element.get(1) + (i*100)) && (carte.get(panneau).get(0) == 25 || carte.get(panneau).get(0) == constante.get(0) || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || carte.get(panneau).get(0) == 20)) {
                        array.get(2).set(0, false);
                        array.get(2).set(1, false);
                    } else if (carte.get(panneau).get(2).equals(element.get(2) + (i*100)) && carte.get(panneau).get(1).equals(element.get(1) - (i*100)) && (carte.get(panneau).get(0) == 25 || carte.get(panneau).get(0) == constante.get(0) || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || carte.get(panneau).get(0) == 20)) {
                        array.get(3).set(0, false);
                        array.get(3).set(1, false);
                    }
                }
                for (JPanel panneau : carte.keySet()) {
                    if (carte.get(panneau).get(2).equals(element.get(2) + (i*100)) && carte.get(panneau).get(1).equals(element.get(1) + (i*100)) && (carte.get(panneau).get(0) == 10 || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == constante.get(1))) {
                        if (array.get(0).get(0).equals(true)) {
                            array.get(0).set(1, false);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2) - (i*100)) && carte.get(panneau).get(1).equals(element.get(1) - (i*100)) && (carte.get(panneau).get(0) == 10 || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == constante.get(1))) {
                        if (array.get(1).get(0).equals(true)) {
                            array.get(1).set(1, false);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2) - (i*100)) && carte.get(panneau).get(1).equals(element.get(1) + (i*100)) && (carte.get(panneau).get(0) == 10 || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11|| carte.get(panneau).get(0) == constante.get(1) )) {
                        if (array.get(2).get(0).equals(true)) {
                            array.get(2).set(1, false);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2) + (i*100)) && carte.get(panneau).get(1).equals(element.get(1) - (i*100)) && (carte.get(panneau).get(0) == 10 || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == constante.get(1))) {
                        if (array.get(3).get(0).equals(true)) {
                            array.get(3).set(1, false);
                            elementsConcernes.add(nombre);
                        }
                    } 
                    nombre++;
                }
                for (int o = 0; o < array.size(); o++) {
                    if (array.get(o).get(1).equals(false)) {
                        array.get(o).set(0, false);
                    }
                }
            }
        } else if (element.get(0) == 23) {
            List<List<Boolean>> array = new ArrayList<>(); 
            array.add(new ArrayList<>(Arrays.asList(true, true)));
            array.add(new ArrayList<>(Arrays.asList(true, true)));
            array.add(new ArrayList<>(Arrays.asList(true, true)));
            array.add(new ArrayList<>(Arrays.asList(true, true)));
            array.add(new ArrayList<>(Arrays.asList(true, true)));
            array.add(new ArrayList<>(Arrays.asList(true, true)));
            array.add(new ArrayList<>(Arrays.asList(true, true)));
            array.add(new ArrayList<>(Arrays.asList(true, true)));
            for (int i = 1; i < 9; i++) {
                int nombre = 0;  
                for (JPanel panneau : carte.keySet()) {
                    if (carte.get(panneau).get(2).equals(element.get(2) + (i*100)) && carte.get(panneau).get(1).equals(element.get(1) + (i*100)) && (carte.get(panneau).get(0) == 25 || carte.get(panneau).get(0) == constante.get(0)  || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || carte.get(panneau).get(0) == 20)) {
                        array.get(0).set(0, false);
                        array.get(0).set(1, false);
                    } else if (carte.get(panneau).get(2).equals(element.get(2) - (i*100)) && carte.get(panneau).get(1).equals(element.get(1) - (i*100)) && (carte.get(panneau).get(0) == 25 || carte.get(panneau).get(0) == constante.get(0) || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || carte.get(panneau).get(0) == 20)) {
                        array.get(1).set(0, false);
                        array.get(1).set(1, false);
                    } else if (carte.get(panneau).get(2).equals(element.get(2) - (i*100)) && carte.get(panneau).get(1).equals(element.get(1) + (i*100)) && (carte.get(panneau).get(0) == 25 || carte.get(panneau).get(0) == constante.get(0) || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || carte.get(panneau).get(0) == 20)) {
                        array.get(2).set(0, false);
                        array.get(2).set(1, false);
                    } else if (carte.get(panneau).get(2).equals(element.get(2) + (i*100)) && carte.get(panneau).get(1).equals(element.get(1) - (i*100)) && (carte.get(panneau).get(0) == 25 || carte.get(panneau).get(0) == constante.get(0) || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || carte.get(panneau).get(0) == 20)) {
                        array.get(3).set(0, false);
                        array.get(3).set(1, false);
                    } else if (carte.get(panneau).get(2).equals(element.get(2) + (i*100)) && carte.get(panneau).get(1).equals(element.get(1)) && (carte.get(panneau).get(0) == 25 || carte.get(panneau).get(0) == constante.get(0)  || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || carte.get(panneau).get(0) == 20)) {
                        array.get(4).set(0, false);
                        array.get(4).set(1, false);
                    } else if (carte.get(panneau).get(2).equals(element.get(2) - (i*100)) && carte.get(panneau).get(1).equals(element.get(1)) && (carte.get(panneau).get(0) == 25 || carte.get(panneau).get(0) == constante.get(0) || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || carte.get(panneau).get(0) == 20)) {
                        array.get(5).set(0, false);
                        array.get(5).set(1, false);
                    } else if (carte.get(panneau).get(2).equals(element.get(2)) && carte.get(panneau).get(1).equals(element.get(1) + (i*100)) && (carte.get(panneau).get(0) == 25 || carte.get(panneau).get(0) == constante.get(0) || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || carte.get(panneau).get(0) == 20)) {
                        array.get(6).set(0, false);
                        array.get(6).set(1, false);
                    } else if (carte.get(panneau).get(2).equals(element.get(2)) && carte.get(panneau).get(1).equals(element.get(1) - (i*100)) && (carte.get(panneau).get(0) == 25 || carte.get(panneau).get(0) == constante.get(0) || carte.get(panneau).get(0) == 24 || carte.get(panneau).get(0) == 23 || carte.get(panneau).get(0) == 22 || carte.get(panneau).get(0) == 21 || carte.get(panneau).get(0) == 20)) {
                        array.get(7).set(0, false);
                        array.get(7).set(1, false);
                    }
                }
                for (JPanel panneau : carte.keySet()) {
                    if (carte.get(panneau).get(2).equals(element.get(2) + (i*100)) && carte.get(panneau).get(1).equals(element.get(1) + (i*100)) && (carte.get(panneau).get(0) == 10 || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == constante.get(1))) {
                        if (array.get(0).get(0).equals(true)) {
                            array.get(0).set(1, false);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2) - (i*100)) && carte.get(panneau).get(1).equals(element.get(1) - (i*100)) && (carte.get(panneau).get(0) == 10 || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == constante.get(1))) {
                        if (array.get(1).get(0).equals(true)) {
                            array.get(1).set(1, false);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2) - (i*100)) && carte.get(panneau).get(1).equals(element.get(1) + (i*100)) && (carte.get(panneau).get(0) == 10 || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == constante.get(1))) {
                        if (array.get(2).get(0).equals(true)) {
                            array.get(2).set(1, false);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2) + (i*100)) && carte.get(panneau).get(1).equals(element.get(1) - (i*100)) && (carte.get(panneau).get(0) == 10 || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == constante.get(1))) {
                        if (array.get(3).get(0).equals(true)) {
                            array.get(3).set(1, false);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2) + (i*100)) && carte.get(panneau).get(1).equals(element.get(1)) && (carte.get(panneau).get(0) == 10 || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == constante.get(1))) {
                        if (array.get(4).get(0).equals(true)) {
                            array.get(4).set(1, false);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2) - (i*100)) && carte.get(panneau).get(1).equals(element.get(1)) && (carte.get(panneau).get(0) == 10 || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == constante.get(1))) {
                        if (array.get(5).get(0).equals(true)) {
                            array.get(5).set(1, false);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2)) && carte.get(panneau).get(1).equals(element.get(1) + (i*100)) && (carte.get(panneau).get(0) == 10 || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11|| carte.get(panneau).get(0) == constante.get(1) )) {
                        if (array.get(6).get(0).equals(true)) {
                            array.get(6).set(1, false);
                            elementsConcernes.add(nombre);
                        }
                    } else if (carte.get(panneau).get(2).equals(element.get(2)) && carte.get(panneau).get(1).equals(element.get(1) - (i*100)) && (carte.get(panneau).get(0) == 10 || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == constante.get(1))) {
                        if (array.get(7).get(0).equals(true)) {
                            array.get(7).set(1, false);
                            elementsConcernes.add(nombre);
                        }
                    } 
                    nombre++;
                }
                for (int o = 0; o < array.size(); o++) {
                    if (array.get(o).get(1).equals(false)) {
                        array.get(o).set(0, false);
                    }
                }
            }
        } else if (element.get(0) == 25) {
            int nombre = 0;
            for (JPanel panneau : carte.keySet()) {
                if (carte.get(panneau).get(2).equals(element.get(2) - 100) && carte.get(panneau).get(1).equals(element.get(1)) && (carte.get(panneau).get(0) == 10 || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == constante.get(1))) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) + 100) && carte.get(panneau).get(1).equals(element.get(1)) && (carte.get(panneau).get(0) == 10 || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11|| carte.get(panneau).get(0) == constante.get(1) )) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) - 100) && carte.get(panneau).get(1).equals(element.get(1) + 100) && (carte.get(panneau).get(0) == 10 || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == constante.get(1))) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) - 100) && carte.get(panneau).get(1).equals(element.get(1) - 100) && (carte.get(panneau).get(0) == 10 || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == constante.get(1))) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) + 100) && carte.get(panneau).get(1).equals(element.get(1) + 100) && (carte.get(panneau).get(0) == 10 || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == constante.get(1))) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2) + 100) && carte.get(panneau).get(1).equals(element.get(1) - 100) && (carte.get(panneau).get(0) == 10 || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11|| carte.get(panneau).get(0) == constante.get(1) )) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2)) && carte.get(panneau).get(1).equals(element.get(1) + 100) && (carte.get(panneau).get(0) == 10 || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11|| carte.get(panneau).get(0) == constante.get(1) )) {
                    elementsConcernes.add(nombre);
                } else if (carte.get(panneau).get(2).equals(element.get(2)) && carte.get(panneau).get(1).equals(element.get(1) - 100) && (carte.get(panneau).get(0) == 10 || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == constante.get(1))) {
                    elementsConcernes.add(nombre);
                } 
                nombre += 1;
            }
        }
        return elementsConcernes;
    }
    public List<Integer> menaceQueen(Map<JPanel, List<Integer>> carte, List<Integer> element, List<Integer> mesures ) {
        int p = 0;
        List<Integer> listeFinale = new ArrayList<>();
        jeuDechec ex = new jeuDechec();
        int nombre = 0;
        for (JPanel thing : carte.keySet()) {
            if (mesures.contains(nombre)) {
                int x = element.get(0);
                int y = carte.get(thing).get(0);
                carte.get(thing).set(0, element.get(0));
                for (JPanel e : carte.keySet()) {
                    if (carte.get(e).equals(element)) {
                        carte.get(e).set(0, 0);
                        break;
                    }
                }
                p = 0;
                for (JPanel panneau : carte.keySet()) {
                    if (carte.get(panneau).get(0) == 25) {
                        break;
                    }
                    p++;
                }
                List<Integer> listePrimaire = new ArrayList<>();
                for (JPanel e : carte.keySet()) {
                    if (carte.get(e).get(0) != 0) {
                        List<Integer> listeSecondaire = ex.calculeInDangerCasesBlack(carte, carte.get(e), List.of(75, 25)); 
                        listePrimaire.addAll(listeSecondaire);
                        if (listeSecondaire.contains(p)) {
                            break;
                        }
                    }
                }
                element.set(0, 0);
                if (!listePrimaire.contains(p)) {
                    listeFinale.add(nombre);
                }
                carte.get(thing).set(0, y);
                for (JPanel e : carte.keySet()) {
                    if (carte.get(e).equals(element)) {
                        carte.get(e).set(0, x);
                        break;
                    }
                } 
                element.set(0, x);
            }
            nombre++;       
        }
        return listeFinale;
    }
    public List<Integer> bot(Map<JPanel, List<Integer>> carte) {
        List<List<Integer>> liste = new ArrayList<>();
        jeuDechec ex = new jeuDechec();
        int n = 0;
        for (JPanel panneau : carte.keySet()) {
            if (carte.get(panneau).get(0) == 10 || carte.get(panneau).get(0) == 14 || carte.get(panneau).get(0) == 13 || carte.get(panneau).get(0) == 12 || carte.get(panneau).get(0) == 11 || carte.get(panneau).get(0) == 15) {
                int score = 0;
                List<Integer> possibilitees = ex.calculeConcernedCasesBlack(carte, carte.get(panneau));
                List<Integer> possibilitees2 = ex.calculeInDangerCasesBlack(carte, carte.get(panneau), List.of(75, 25));
                possibilitees.addAll(possibilitees2);
                int nombre = 0;
                for (JPanel thing : carte.keySet()) {
                    for (int i = 0; i < possibilitees.size(); i++) {
                        if (nombre != n) {
                            if (nombre == possibilitees.get(i)) {
                                double d = Math.random()*5;
                                score -= d;
                                if (carte.get(thing).get(0) == 20 || carte.get(thing).get(0) == 21 || carte.get(thing).get(0) == 22) {
                                    score += 500; 
                                } else if (carte.get(thing).get(0) == 23) {
                                    score += 1000;
                                } else if (carte.get(thing).get(0) == 24) {
                                    score += 50;
                                }        
                                if (carte.get(thing).get(2) > carte.get(panneau).get(2)) {
                                    score += (carte.get(thing).get(2) - carte.get(panneau).get(2)) / 25;                            
                                }
                                int intermediaire = carte.get(panneau).get(0);
                                int intermediaire2 = carte.get(thing).get(0);
                                carte.get(panneau).set(0, 0);
                                carte.get(thing).set(0, intermediaire);
                                List<Integer> danger = new ArrayList<>();
                                List<Integer> aide = new ArrayList<>();
                                for (JPanel p : carte.keySet()) {
                                    if (carte.get(p).get(0) != 0) {
                                        List<Integer> temporaire = ex.calculeInDangerCases(carte, carte.get(p), List.of(75, 15));
                                        danger.addAll(temporaire);
                                    }
                                }
                                for (JPanel p : carte.keySet()) {
                                    List<Integer> temporaire = ex.calculeInDangerCasesBlack(carte, carte.get(p), List.of(75, 25));
                                    aide.addAll(temporaire); 
                                }
                                int n1 = 0;
                                int n2 = 0;
                                for (JPanel p : carte.keySet()) {
                                    if (carte.get(p).get(0) == 25) {
                                        if (aide.contains(n1)) {
                                            score += 700;
                                            break;
                                        }
                                    }
                                    n1++;
                                }
                                boolean reineMenacee = false;
                                for (JPanel p : carte.keySet()) {
                                    if (carte.get(p).get(0) == 15) {
                                        if (danger.contains(n2)) {
                                            reineMenacee = true;
                                            break;
                                        }
                                    } else if (carte.get(p).get(0) == 14) {
                                        if (danger.contains(n2)) {
                                            if (aide.contains(n2)) {
                                                score -= 10;
                                            } else {
                                                score -= 30;
                                            }
                                        }
                                    } else if (carte.get(p).get(0) == 13) {
                                        if (danger.contains(n2)) {
                                            if (aide.contains(n2)) {
                                                score -= 550;
                                            } else {
                                                score -= 950;
                                            }
                                        }
                                    } else if (carte.get(p).get(0) == 12) {
                                        if (danger.contains(n2)) {
                                            if (aide.contains(n2)) {
                                                score -= 250;
                                            } else {
                                                score -= 475;
                                            }
                                        }
                                    } else if (carte.get(p).get(0) == 11) {
                                        if (danger.contains(n2)) {
                                            if (aide.contains(n2)) {
                                                score -= 250;
                                            } else {
                                                score -= 475;
                                            }
                                        }
                                    } else if (carte.get(p).get(0) == 10) {
                                        if (danger.contains(n2)) {
                                            if (aide.contains(n2)) {
                                                score -= 250;
                                            } else {
                                                score -= 475;
                                            }
                                        }
                                    }
                                    n2++;
                                }
                                carte.get(panneau).set(0, intermediaire);
                                carte.get(thing).set(0, intermediaire2);
                                if (!reineMenacee) {
                                    List<Integer> result = new ArrayList<>();
                                    result.add(n);
                                    result.add(nombre);
                                    result.add(score);
                                    liste.add(result);
                                    score = 0;
                                    break;
                                }
                                break;
                            }
                        }
                    }
                    nombre++;
                }
            }
            n++;
        }
        for (int i = 0; i < liste.size(); i++) {
            for (int o = 0; o < liste.size(); o++) {
                if (o > 0) {
                    if (liste.get(o).get(2) > liste.get(o-1).get(2)) {
                        List<Integer> intermediaire = liste.get(o);
                        liste.set(o, liste.get(o-1));
                        liste.set(o-1, intermediaire);
                    }
                }
            }
        }
        if (liste.isEmpty()) {
            return null;
        }
        return liste.get(0);
    }
    public static void main(String[] args) {
        List<Boolean> RobotAPerdu = new ArrayList<>(List.of(false));
        List<Boolean> HumainAPerdu = new ArrayList<>(List.of(false));
        List<Integer> CoupsInactifs = new ArrayList<>(List.of(0));
        JFrame frame = new JFrame("Jeu d'echec");
        frame.setSize(817, 840);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.WHITE);
        ImageIcon imageicon = new ImageIcon("images.jpg");
        frame.setIconImage(imageicon.getImage());
        Map<JPanel, List<Integer>> panneaux = new LinkedHashMap<>();
        boolean variable = true;
        jeuDechec ex = new jeuDechec();
        for (int i = 0; i < 8; i++) {
            int v = 0;
            for (double o = 0; o < 8; o+=1) {
                v++;
                if (variable) {
                    variable = !variable;
                    JPanel panel = new JPanel(){
                        @Override
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g);
                            Graphics2D g2d = (Graphics2D) g;
                            g2d.setClip(null);
                        }
                    };
                    panel.setPreferredSize(new Dimension(100,100));
                    panel.setBounds(i*100+1, v*100+1-100, 100, 100);
                    panel.setBackground(Color.WHITE);
                    panel.setLayout(new BorderLayout());
                    List<Integer> liste = new ArrayList<>();
                    liste.add(0);
                    liste.add(i*100);
                    liste.add(v*100);
                    liste.add(0);
                    liste.add(0);
                    if (v == 1 && i == 7 || v == 1 && i == 0) {
                        JLabel label = ex.creerJLabel("tourNoire.svg", 100, 100);
                        label.setHorizontalAlignment(JLabel.LEFT);
                        label.setVerticalAlignment(JLabel.CENTER);
                        liste.set(0, 10);
                        panel.add(label);
                    } else if (v == 1 && i == 6 || v == 1 && i == 1) {
                        JLabel label = ex.creerJLabel("chevalNoir.svg", 100, 100);
                        label.setHorizontalAlignment(JLabel.LEFT);
                        label.setVerticalAlignment(JLabel.CENTER);
                        panel.add(label);
                        liste.set(0, 11);
                    } else if (v == 1 && i == 5 || v == 1 && i == 2) {
                        JLabel label = ex.creerJLabel("fouNoir.svg", 100, 100);
                        label.setHorizontalAlignment(JLabel.LEFT);
                        label.setVerticalAlignment(JLabel.CENTER);
                        panel.add(label);
                        liste.set(0, 12);
                    } else if (v == 1 && i == 4) {
                        JLabel label = ex.creerJLabel("reineNoire.svg", 125, 100);
                        label.setHorizontalAlignment(JLabel.LEFT);
                        label.setVerticalAlignment(SwingConstants.BOTTOM);
                        panel.add(label);
                        liste.set(0, 15);
                    } else if (v == 2) {
                        JLabel label = ex.creerJLabel("pionNoir.svg", 125, 100);
                        label.setHorizontalAlignment(JLabel.LEFT);
                        label.setVerticalAlignment(SwingConstants.BOTTOM);
                        panel.add(label);
                        liste.set(0, 14);
                    } else if (v == 7) {
                        JLabel label = ex.creerJLabel("pionBlanc.svg", 125, 100);
                        label.setHorizontalAlignment(JLabel.LEFT);
                        label.setVerticalAlignment(SwingConstants.BOTTOM);
                        panel.add(label);
                        liste.set(0, 24);
                    } else if (v == 8 && i == 7 || v == 8 && i == 0) {
                        JLabel label = ex.creerJLabel("tourBlanche.svg", 100, 100);
                        label.setHorizontalAlignment(JLabel.LEFT);
                        label.setVerticalAlignment(JLabel.CENTER);
                        panel.add(label);
                        liste.set(0, 20);
                    } else if (v == 8 && i == 6 || v == 8 && i == 1) {
                        JLabel label = ex.creerJLabel("chevalBlanc.svg", 100, 100);
                        label.setHorizontalAlignment(JLabel.LEFT);
                        label.setVerticalAlignment(JLabel.CENTER);
                        panel.add(label);
                        liste.set(0, 21);
                    } else if (v == 8 && i == 5 || v == 8 && i == 2) {
                        JLabel label = ex.creerJLabel("fouBlanc.svg", 100, 100);
                        label.setHorizontalAlignment(JLabel.LEFT);
                        label.setVerticalAlignment(JLabel.CENTER);
                        panel.add(label);
                        liste.set(0, 22);
                    } else if (v == 8 && i == 3) {
                        JLabel label = ex.creerJLabel("reineBlanche.svg", 125, 100);
                        label.setHorizontalAlignment(JLabel.LEFT);
                        label.setVerticalAlignment(SwingConstants.BOTTOM);
                        panel.add(label);
                        liste.set(0, 25);
                    } 
                    panneaux.put(panel, liste);
                } else {
                    variable = !variable;
                    JPanel panel = new JPanel(){
                        @Override
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g);
                            Graphics2D g2d = (Graphics2D) g;
                            g2d.setClip(null);
                        }
                    };
                    panel.setPreferredSize(new Dimension(100, 100));
                    panel.setBounds(i*100+1, v*100+1-100, 100, 100);
                    panel.setBackground(Color.LIGHT_GRAY);
                    panel.setLayout(new BorderLayout());
                    List<Integer> liste = new ArrayList<>();
                    liste.add(0);
                    liste.add(i*100);
                    liste.add(v*100);
                    liste.add(1);
                    liste.add(0);
                    liste.add(i*10+v);
                    if (v == 1 && i == 7 || v == 1 && i == 0) {
                        JLabel label = ex.creerJLabel("tourNoire.svg", 100, 100);
                        label.setHorizontalAlignment(JLabel.LEFT);
                        label.setVerticalAlignment(JLabel.CENTER);
                        panel.add(label);
                        liste.set(0, 10);
                    } else if (v == 1 && i == 6 || v == 1 && i == 1) {
                        JLabel label = ex.creerJLabel("chevalNoir.svg", 100, 100);
                        label.setHorizontalAlignment(JLabel.LEFT);
                        label.setVerticalAlignment(JLabel.CENTER);
                        panel.add(label);
                        liste.set(0, 11);
                    } else if (v == 1 && i == 5 || v == 1 && i == 2) {
                        JLabel label = ex.creerJLabel("fouNoir.svg", 100, 100);
                        label.setHorizontalAlignment(JLabel.LEFT);
                        label.setVerticalAlignment(JLabel.CENTER);
                        panel.add(label);
                        liste.set(0, 12);
                    } else if (v == 1 && i == 3) {
                        JLabel label = ex.creerJLabel("roiNoir.svg", 125, 100);
                        label.setHorizontalAlignment(JLabel.LEFT);
                        label.setVerticalAlignment(SwingConstants.BOTTOM);
                        panel.add(label);
                        liste.set(0, 13);
                    } else if (v == 2) {
                        JLabel label = ex.creerJLabel("pionNoir.svg", 125, 100);
                        label.setHorizontalAlignment(JLabel.LEFT);
                        label.setVerticalAlignment(SwingConstants.BOTTOM);
                        panel.add(label);
                        liste.set(0, 14);
                    } else if (v == 7) {
                        JLabel label = ex.creerJLabel("pionBlanc.svg", 125, 100);
                        label.setHorizontalAlignment(JLabel.LEFT);
                        label.setVerticalAlignment(SwingConstants.BOTTOM);
                        panel.add(label);
                        liste.set(0, 24);
                    } else if (v == 8 && i == 7 || v == 8 && i == 0) {
                        JLabel label = ex.creerJLabel("tourBlanche.svg", 100, 100);
                        label.setHorizontalAlignment(JLabel.LEFT);
                        label.setVerticalAlignment(JLabel.CENTER);
                        panel.add(label);
                        liste.set(0, 20);
                    } else if (v == 8 && i == 6 || v == 8 && i == 1) {
                        JLabel label = ex.creerJLabel("chevalBlanc.svg", 100, 100);
                        label.setHorizontalAlignment(JLabel.LEFT);
                        label.setVerticalAlignment(JLabel.CENTER);
                        panel.add(label);
                        liste.set(0, 21);
                    } else if (v == 8 && i == 5 || v == 8 && i == 2) {
                        JLabel label = ex.creerJLabel("fouBlanc.svg", 100, 100);
                        label.setHorizontalAlignment(JLabel.LEFT);
                        label.setVerticalAlignment(JLabel.CENTER);
                        panel.add(label);
                        liste.set(0, 22);
                    } else if (v == 8 && i == 4) {
                        JLabel label = ex.creerJLabel("roiBlanc.svg", 125, 100);
                        label.setHorizontalAlignment(JLabel.LEFT);
                        label.setVerticalAlignment(SwingConstants.BOTTOM);
                        panel.add(label);
                        liste.set(0, 23);
                    } 
                    panneaux.put(panel, liste);
                }
            }
            variable = !variable;
        }
        for (JPanel panel : panneaux.keySet()) {
            frame.add(panel);
        }
        for (JPanel x : panneaux.keySet()) {
            x.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JPanel panel = (JPanel) e.getSource();
                    if (panneaux.get(panel).get(0) == 0) {
                        for (JPanel element : panneaux.keySet()) {
                            if (panneaux.get(element).get(4) == 1) {
                                if (panneaux.get(panel).get(3) == 3 || panneaux.get(panel).get(3) == 4) {
                                    CoupsInactifs.set(0, CoupsInactifs.get(0) + 1);
                                    int number = 0;
                                    String path = "";
                                    if (panneaux.get(element).get(0) == 24) {
                                        path = "pionBlanc.svg";
                                        number = 24;
                                    } 
                                    else if (panneaux.get(element).get(0) == 21) {
                                        path = "chevalBlanc.svg";
                                        number = 21;
                                    } else if (panneaux.get(element).get(0) == 20) {
                                        path = "tourBlanche.svg";
                                        number = 20;
                                    } else if (panneaux.get(element).get(0) == 22) {
                                        path = "fouBlanc.svg";
                                        number = 22;
                                    } else if (panneaux.get(element).get(0) == 23) {
                                        path = "roiBlanc.svg";
                                        number = 23;
                                    } else if (panneaux.get(element).get(0) == 25) {
                                        path = "reineBlanche.svg";
                                        number = 25;
                                    }
                                    JLabel label = ex.creerJLabel(path, 100, 100);
                                    if (label != null) {
                                        for (Component x : element.getComponents()) {
                                            if (x instanceof JLabel) {
                                                element.remove(x);
                                                break;
                                            }
                                        }
                                        label.setHorizontalAlignment(SwingConstants.LEFT);
                                        label.setVerticalAlignment(JLabel.CENTER);
                                        panel.add(label);
                                        panneaux.get(panel).set(0, number);
                                        panneaux.get(element).set(0, 0);
                                        if (panneaux.get(panel).get(3) == 4) {
                                            panneaux.get(panel).set(3, 0);
                                            panel.setBackground(Color.WHITE);
                                        } else if (panneaux.get(panel).get(3) == 3) {
                                            panneaux.get(panel).set(3,1);
                                            panel.setBackground(Color.LIGHT_GRAY);
                                        } else if (panneaux.get(panel).get(3) == 6) {
                                            panneaux.get(panel).set(3, 0);
                                            panel.setBackground(Color.WHITE);
                                        } else if (panneaux.get(panel).get(3) == 5) {
                                            panneaux.get(panel).set(3,1);
                                            panel.setBackground(Color.LIGHT_GRAY);
                                        }
                                        if (panneaux.get(element).get(3) == 4) {
                                            panneaux.get(element).set(3, 0);
                                            element.setBackground(Color.WHITE);
                                        } else if (panneaux.get(element).get(3) == 3) {
                                            panneaux.get(element).set(3,1);
                                            element.setBackground(Color.LIGHT_GRAY);
                                        } else if (panneaux.get(element).get(3) == 6) {
                                            panneaux.get(element).set(3, 0);
                                            element.setBackground(Color.WHITE);
                                        } else if (panneaux.get(element).get(3) == 5) {
                                            panneaux.get(element).set(3,1);
                                            element.setBackground(Color.LIGHT_GRAY);
                                        }
                                        panel.revalidate();
                                        panel.repaint();
                                        element.revalidate();
                                        element.repaint();
                                        int n2 = 0;
                                        for (JPanel p : panneaux.keySet()) {
                                            if (panneaux.get(p).get(0) == 15) {
                                                List<Integer> danger = new ArrayList<>();
                                                for (JPanel panneau : panneaux.keySet()) {
                                                    List<Integer> temporaire = ex.calculeInDangerCases(panneaux, panneaux.get(panneau), List.of(75, 15));
                                                    danger.addAll(temporaire);
                                                }
                                                if (danger.contains(n2)) {
                                                    p.setBackground(new Color(255, 45, 15));
                                                    p.repaint();
                                                }
                                                break;
                                            }
                                            n2++;
                                        }
                                        List<Integer> response = ex.bot(panneaux);
                                        if (response != null) {
                                            int nombre = 0;
                                            for (JPanel thing : panneaux.keySet()) {
                                                if (nombre == response.get(0)) {
                                                    int n = 0;
                                                    for (JPanel chose : panneaux.keySet()) {
                                                        if (n == response.get(1)) {
                                                            for (Component c : thing.getComponents()) {
                                                                if (c instanceof JLabel) {
                                                                    thing.remove(c);
                                                                    break;
                                                                }
                                                            }
                                                            for (Component j : chose.getComponents()) {
                                                                if (j instanceof JLabel) {
                                                                    chose.remove(j);
                                                                    break;
                                                                }
                                                            }
                                                            JLabel labelTemporaire = new JLabel();
                                                            if (panneaux.get(thing).get(0) == 10) {
                                                                labelTemporaire = ex.creerJLabel("tourNoire.svg", 100, 100);
                                                            } else if (panneaux.get(thing).get(0) == 11) {
                                                                labelTemporaire = ex.creerJLabel("chevalNoir.svg", 100, 100);
                                                            } else if (panneaux.get(thing).get(0) == 12) {
                                                                labelTemporaire = ex.creerJLabel("fouNoir.svg", 100, 100);
                                                            } else if (panneaux.get(thing).get(0) == 13) {
                                                                labelTemporaire = ex.creerJLabel("roiNoir.svg", 100, 100);
                                                            } else if (panneaux.get(thing).get(0) == 14) {
                                                                labelTemporaire = ex.creerJLabel("pionNoir.svg", 100, 100);
                                                            } else if (panneaux.get(thing).get(0) == 15) {
                                                                labelTemporaire = ex.creerJLabel("reineNoire.svg", 100, 100);
                                                            }
                                                            if (panneaux.get(chose).get(0) == 0) {
                                                                CoupsInactifs.set(0, CoupsInactifs.get(0) + 1);
                                                            } else {
                                                                CoupsInactifs.set(0, 0);
                                                            }
                                                            int intermediaire = panneaux.get(thing).get(0);
                                                            panneaux.get(thing).set(0, 0);
                                                            panneaux.get(chose).set(0, intermediaire);
                                                            chose.add(labelTemporaire);
                                                            labelTemporaire.setVerticalAlignment(SwingConstants.CENTER);
                                                            labelTemporaire.setHorizontalAlignment(SwingConstants.CENTER);
                                                            chose.revalidate();
                                                            chose.repaint();
                                                            thing.revalidate();
                                                            thing.repaint();
                                                            break;
                                                            }
                                                            n++;
                                                        }
                                                    break;
                                                }
                                                nombre++;
                                            }
                                        } else {
                                            RobotAPerdu.set(0, true);
                                        }
                                    }                                    
                                }
                            }
                        }
                    } else if (panneaux.get(panel).get(0) != 0) {
                        for (JPanel element : panneaux.keySet()) {
                            if (panneaux.get(element).get(4) == 1) {
                                if (panneaux.get(panel).get(3) == 5 || panneaux.get(panel).get(3) == 6) {
                                    CoupsInactifs.set(0, 0);
                                    int number = 0;
                                    String path = "";
                                    if (panneaux.get(element).get(0) == 24) {
                                        path = "pionBlanc.svg";
                                        number = 24;
                                    }
                                    else if (panneaux.get(element).get(0) == 21) {
                                        path = "chevalBlanc.svg";
                                        number = 21;
                                    } else if (panneaux.get(element).get(0) == 20) {
                                        path = "tourBlanche.svg";
                                        number = 20;
                                    } else if (panneaux.get(element).get(0) == 22) {
                                        path = "fouBlanc.svg";
                                        number = 22;
                                    } else if (panneaux.get(element).get(0) == 23) {
                                        path = "roiBlanc.svg";
                                        number = 23;
                                    } else if (panneaux.get(element).get(0) == 25) {
                                        path = "reineBlanche.svg";
                                        number = 25;
                                    }
                                    JLabel label = ex.creerJLabel(path, 100, 100);
                                    if (label != null) {
                                        for (Component x : element.getComponents()) {
                                            if (x instanceof JLabel) {
                                                element.remove(x);
                                                break;
                                            }
                                        }
                                        for (Component x : panel.getComponents()) {
                                            if (x instanceof JLabel) {
                                                panel.remove(x);
                                                break;
                                            }
                                        }
                                        label.setHorizontalAlignment(SwingConstants.LEFT);
                                        label.setVerticalAlignment(JLabel.CENTER);
                                        panel.add(label);
                                        panneaux.get(panel).set(0, number);
                                        panneaux.get(element).set(0, 0);
                                        if (panneaux.get(panel).get(3) == 6) {
                                            panneaux.get(panel).set(3, 0);
                                            panel.setBackground(Color.WHITE);
                                        } else if (panneaux.get(panel).get(3) == 5) {
                                            panneaux.get(panel).set(3,1);
                                            panel.setBackground(Color.LIGHT_GRAY);
                                        } else if (panneaux.get(panel).get(3) == 6) {
                                            panneaux.get(panel).set(3, 0);
                                            panel.setBackground(Color.WHITE);
                                        } else if (panneaux.get(panel).get(3) == 5) {
                                            panneaux.get(panel).set(3,1);
                                            panel.setBackground(Color.LIGHT_GRAY);
                                        }
                                        if (panneaux.get(element).get(3) == 6) {
                                            panneaux.get(element).set(3, 0);
                                            element.setBackground(Color.WHITE);
                                        } else if (panneaux.get(element).get(3) == 5) {
                                            panneaux.get(element).set(3,1);
                                            element.setBackground(Color.LIGHT_GRAY);
                                        } else if (panneaux.get(element).get(3) == 6) {
                                            panneaux.get(element).set(3, 0);
                                            element.setBackground(Color.WHITE);
                                        } else if (panneaux.get(element).get(3) == 5) {
                                            panneaux.get(element).set(3,1);
                                            element.setBackground(Color.LIGHT_GRAY);
                                        }
                                        panel.revalidate();
                                        panel.repaint();
                                        element.revalidate();
                                        element.repaint();
                                        int n2 = 0;
                                        for (JPanel p : panneaux.keySet()) {
                                            if (panneaux.get(p).get(0) == 15) {
                                                List<Integer> danger = new ArrayList<>();
                                                for (JPanel panneau : panneaux.keySet()) {
                                                    List<Integer> temporaire = ex.calculeInDangerCases(panneaux, panneaux.get(panneau), List.of(75, 15));
                                                    danger.addAll(temporaire);
                                                }
                                                if (danger.contains(n2)) {
                                                    p.setBackground(new Color(255, 45, 15));
                                                }
                                                p.repaint();
                                                break;
                                            }
                                            n2++;
                                        }
                                        List<Integer> response = ex.bot(panneaux);
                                        if (response != null) {
                                            int nombre = 0;
                                            for (JPanel thing : panneaux.keySet()) {
                                                if (nombre == response.get(0)) {
                                                    int n = 0;
                                                    for (JPanel chose : panneaux.keySet()) {
                                                        if (n == response.get(1)) {
                                                            for (Component c : thing.getComponents()) {
                                                                if (c instanceof JLabel) {
                                                                    thing.remove(c);
                                                                    break;
                                                                }
                                                            }
                                                            for (Component j : chose.getComponents()) {
                                                                if (j instanceof JLabel) {
                                                                    chose.remove(j);
                                                                    break;
                                                                }
                                                            }
                                                            JLabel labelTemporaire = new JLabel();
                                                            if (panneaux.get(thing).get(0) == 10) {
                                                                labelTemporaire = ex.creerJLabel("tourNoire.svg", 100, 100);
                                                            } else if (panneaux.get(thing).get(0) == 11) {
                                                                labelTemporaire = ex.creerJLabel("chevalNoir.svg", 100, 100);
                                                            } else if (panneaux.get(thing).get(0) == 12) {
                                                                labelTemporaire = ex.creerJLabel("fouNoir.svg", 100, 100);
                                                            } else if (panneaux.get(thing).get(0) == 13) {
                                                                labelTemporaire = ex.creerJLabel("roiNoir.svg", 100, 100);
                                                            } else if (panneaux.get(thing).get(0) == 14) {
                                                                labelTemporaire = ex.creerJLabel("pionNoir.svg", 100, 100);
                                                            } else if (panneaux.get(thing).get(0) == 15) {
                                                                labelTemporaire = ex.creerJLabel("reineNoire.svg", 100, 100);
                                                            }
                                                            if (panneaux.get(chose).get(0) == 0) {
                                                                CoupsInactifs.set(0, CoupsInactifs.get(0) + 1);
                                                            } else {
                                                                CoupsInactifs.set(0, 0);
                                                            }
                                                            int intermediaire = panneaux.get(thing).get(0);
                                                            panneaux.get(thing).set(0, 0);
                                                            panneaux.get(chose).set(0, intermediaire);
                                                            labelTemporaire.setVerticalAlignment(SwingConstants.CENTER);
                                                            labelTemporaire.setHorizontalAlignment(SwingConstants.CENTER);
                                                            chose.add(labelTemporaire);
                                                            chose.revalidate();
                                                            chose.repaint();
                                                            thing.revalidate();
                                                            thing.repaint();
                                                            break;
                                                            }
                                                            n++;
                                                        }
                                                    break;
                                                }
                                                nombre++;
                                            }
                                        } else {
                                            RobotAPerdu.set(0, true);
                                        }
                                    }                                    
                                }
                            }
                        }
                    }
                    for (JPanel element : panneaux.keySet()) {
                        if (panneaux.get(element).get(0) != 0) {
                            boolean maVariable = false;
                            for (Component c : element.getComponents()) {
                                if (c instanceof JLabel) {
                                    maVariable = true;
                                    break;
                                }
                            }
                            if (maVariable == false) {
                                panneaux.get(element).set(0, 0);
                            }
                        }
                    }
                    for (JPanel element : panneaux.keySet()) {
                        if (panneaux.get(element).get(4) == 1) {
                            panneaux.get(element).set(4, 0);
                        }
                        if (panneaux.get(element).get(3) == 3) {
                            element.setBackground(Color.LIGHT_GRAY);
                        } else if (panneaux.get(element).get(3) == 4) {
                            element.setBackground(Color.WHITE);
                        } 
                        if (panneaux.get(element).get(3) == 5) {
                            element.setBackground(Color.LIGHT_GRAY);
                        } else if (panneaux.get(element).get(3) == 6) {
                            element.setBackground(Color.WHITE);
                        } 
                        if (panneaux.get(element).get(3) == 1) {
                            element.setBackground(Color.LIGHT_GRAY);
                        } else if (panneaux.get(element).get(3) == 0) {
                            element.setBackground(Color.WHITE);
                        }
                        element.repaint();
                    }
                    if (panneaux.get(panel).get(4) == 0 && panneaux.get(panel).get(0) != 0) {
                        List<Integer> resultats = ex.calculeConcernedCases(panneaux, panneaux.get(panel));
                        List<Integer> cases = ex.calculeInDangerCases(panneaux, panneaux.get(panel), List.of(25, 75));
                        cases = ex.menaceQueen(panneaux, panneaux.get(panel), cases);
                        resultats = ex.menaceQueen(panneaux, panneaux.get(panel), resultats);
                        panneaux.get(panel).set(4, 1);
                        int n = 0;
                        for (JPanel p : panneaux.keySet()) {
                            if (p.equals(panel)) {
                                resultats.add(n);
                                break;
                            }
                            n++;
                        }
                        if (!cases.isEmpty()) {
                            for (JPanel element : panneaux.keySet()) {
                                if (panneaux.get(element).get(3) == 5) {
                                    panneaux.get(element).set(3, 1);
                                    element.setBackground(Color.LIGHT_GRAY);
                                } else if (panneaux.get(element).get(3) == 6) {
                                    panneaux.get(element).set(3, 0);
                                    element.setBackground(Color.WHITE);
                                }
                            }
                            for (int i = 0; i < cases.size(); i++) {
                                int nombre = 0;
                                for (JPanel element : panneaux.keySet()) {
                                    if (nombre == cases.get(i)) {
                                        if (panneaux.get(element).get(3) == 1) {
                                            panneaux.get(element).set(3, 5);
                                            element.setBackground(new Color(255, 125, 75));
                                        } else if (panneaux.get(element).get(3) == 0) {
                                            panneaux.get(element).set(3, 6);
                                            element.setBackground(new Color(255, 125, 75));
                                        } else if (panneaux.get(element).get(3) == 5) {
                                            element.setBackground(new Color(255, 125, 75));
                                        } else if (panneaux.get(element).get(3) == 6) {
                                            element.setBackground(new Color(255, 125, 75));
                                        }
                                    }
                                    nombre++;
                                }
                            }
                        }
                        if (!resultats.isEmpty())  {
                            for (JPanel element : panneaux.keySet()) {
                                if (panneaux.get(element).get(3) == 3) {
                                    panneaux.get(element).set(3, 1);
                                    element.setBackground(Color.LIGHT_GRAY);
                                } else if (panneaux.get(element).get(3) == 4) {
                                    panneaux.get(element).set(3, 0);
                                    element.setBackground(Color.WHITE);
                                }
                            }
                            for (int i = 0; i < resultats.size(); i++) {
                                int nombre = 0;
                                frame.repaint();
                                for (JPanel p : panneaux.keySet()) {
                                    if (nombre == resultats.get(i)) {
                                        if (panneaux.get(p).get(3) == 1) {
                                            panneaux.get(p).set(3, 3);
                                            p.setBackground(new Color(75, 165, 255));
                                        } else if (panneaux.get(p).get(3) == 0) {
                                            panneaux.get(p).set(3, 4);
                                            p.setBackground(new Color(75, 165, 255));
                                        } 
                                    }
                                    nombre += 1;
                                }
                            }
                        }
                        frame.repaint();
                    }
                    List<Integer> listeDeListeTemporaire = new ArrayList<>();
                    for (JPanel p : panneaux.keySet()) {
                        if (panneaux.get(p).get(0) == 20 || panneaux.get(p).get(0) == 21 || panneaux.get(p).get(0) == 22 || panneaux.get(p).get(0) == 23 || panneaux.get(p).get(0) == 24 || panneaux.get(p).get(0) == 25) {
                            List<Integer> temporaire = ex.calculeConcernedCases(panneaux, panneaux.get(p));
                            temporaire = ex.menaceQueen(panneaux, panneaux.get(p), temporaire);
                            listeDeListeTemporaire.addAll(temporaire);
                            if (!temporaire.isEmpty()) {
                                break;
                            }
                            temporaire = ex.calculeInDangerCases(panneaux, panneaux.get(p), List.of(15, 75));
                            temporaire = ex.menaceQueen(panneaux, panneaux.get(p), temporaire);
                            listeDeListeTemporaire.addAll(temporaire);
                            if (!temporaire.isEmpty()) {
                                break;
                            }
                        }
                    }
                    if (listeDeListeTemporaire.isEmpty()) {
                        HumainAPerdu.set(0, true);
                    }
                    int number = 0;
                    for (JPanel element : panneaux.keySet()) { 
                        if (panneaux.get(element).get(0) == 24) {
                            if (panneaux.get(element).get(2) == 100) {
                                for (Component x : element.getComponents()) {
                                    if (x instanceof JLabel) {
                                        element.remove(x);
                                        break;
                                    }
                                }
                                JLabel label = ex.creerJLabel("roiBlanc.svg", 100, 100);
                                label.setHorizontalAlignment(SwingConstants.LEFT);
                                label.setVerticalAlignment(JLabel.CENTER);
                                panel.add(label);
                                panneaux.get(element).set(0, 23);
                                element.add(label);
                                frame.revalidate();
                                frame.repaint();
                            }
                        } else if (panneaux.get(element).get(0) == 14) {
                            if (panneaux.get(element).get(2) == 800) {
                                for (Component x : element.getComponents()) {
                                    if (x instanceof JLabel) {
                                        element.remove(x);
                                        break;
                                    }
                                }
                                JLabel label = ex.creerJLabel("roiNoir.svg", 100, 100);
                                label.setHorizontalAlignment(SwingConstants.LEFT);
                                label.setVerticalAlignment(JLabel.CENTER);
                                panel.add(label);
                                panneaux.get(element).set(0, 13);
                                element.add(label);
                                frame.revalidate();
                                frame.repaint();
                            }
                        } else if (panneaux.get(element).get(0) == 25) {
                            List<Integer> danger = new ArrayList<>();
                            for (JPanel p : panneaux.keySet()) {
                                List<Integer> temporaire = ex.calculeInDangerCasesBlack(panneaux, panneaux.get(p), List.of(75, 25));
                                danger.addAll(temporaire);
                            }
                            if (danger.contains(number)) {
                                element.setBackground(new Color(255, 45, 15));
                            }
                            element.repaint();
                        } else if (panneaux.get(element).get(0) == 15) {
                            List<Integer> danger = new ArrayList<>();
                            for (JPanel panneau : panneaux.keySet()) {
                                List<Integer> temporaire = ex.calculeInDangerCases(panneaux, panneaux.get(panneau), List.of(75, 15));
                                danger.addAll(temporaire);
                            }
                            if (danger.contains(number)) {
                                element.setBackground(new Color(255, 55, 25));
                            }
                            element.repaint();
                        }
                        number++;
                    }
                    if (CoupsInactifs.get(0) > 150 || HumainAPerdu.get(0) == true || RobotAPerdu.get(0) == true) {
                        JPanel overley = new JPanel();
                        overley.setLayout(null);
                        overley.setBackground(new Color(255,255, 255));
                        overley.setBounds(0, 0, frame.getWidth(), frame.getHeight());
                        overley.setOpaque(false);
                        overley.addMouseListener(new MouseAdapter() {});
                        frame.getLayeredPane().add(overley, JLayeredPane.MODAL_LAYER);
                        frame.revalidate();
                        frame.repaint();
                        Timer timer1 = new Timer(1000, time -> {
                            for (JPanel p : panneaux.keySet()) {
                                panneaux.get(p).set(0, 0);
                                for (Component c : p.getComponents()) {
                                    if (c instanceof JLabel) {
                                        p.remove(c);
                                    }
                                }
                                p.setBackground(new Color(255, 255, 255));
                                p.revalidate();
                                p.repaint();
                            }
                            JLabel label = new JLabel();
                            label.setSize(label.getPreferredSize());
                            label.setBounds(75, 300, 600, 150);
                            label.setFont(new Font("Arial", Font.PLAIN, 100));
                            label.setOpaque(true);
                            label.setHorizontalAlignment(SwingConstants.CENTER);
                            label.setBackground(new Color(0, 0, 0, 0));
                            if (CoupsInactifs.get(0) > 150) {
                                System.out.println("egalite");
                                label.setText("game draw!");
                                label.setForeground(new Color(150, 125, 0));
                            } else if (HumainAPerdu.get(0)) {
                                label.setText("game over!");
                                System.out.println("echec et math");
                                label.setForeground(new Color(225, 50, 50));
                            } else if (RobotAPerdu.get(0)) {
                                label.setText("game won!");
                                label.setForeground(new Color(50, 225, 50));
                                System.out.println("victoire");
                            }
                            frame.getLayeredPane().add(label, JLayeredPane.POPUP_LAYER);
                            frame.revalidate();
                            frame.repaint();
                            CoupsInactifs.set(0, 0);
                            HumainAPerdu.set(0, false);
                            RobotAPerdu.set(0, false);
                            Timer timer2 = new Timer(1000, time2 -> {
                                frame.getLayeredPane().remove(overley);
                                frame.getLayeredPane().remove(label);
                                frame.revalidate();
                                frame.repaint();
                                for (JPanel element : panneaux.keySet()) {
                                    if (panneaux.get(element).get(2) == 200) {
                                        JLabel label2 = ex.creerJLabel("pionNoir.svg", 100, 100);
                                        label2.setHorizontalAlignment(JLabel.LEFT);
                                        label2.setVerticalAlignment(JLabel.CENTER);
                                        panneaux.get(element).set(0, 14);
                                        element.add(label2);    
                                    } else if (panneaux.get(element).get(2) == 700) {
                                        JLabel label2 = ex.creerJLabel("pionBlanc.svg", 100, 100);
                                        label2.setHorizontalAlignment(JLabel.LEFT);
                                        label2.setVerticalAlignment(JLabel.CENTER);
                                        element.add(label2);
                                        panneaux.get(element).set(0, 24);    
                                    } else if (panneaux.get(element).get(2) == 800 && (panneaux.get(element).get(1) == 700 || panneaux.get(element).get(1) == 0)) {
                                        JLabel label2 = ex.creerJLabel("tourBlanche.svg", 100, 100);
                                        label2.setHorizontalAlignment(JLabel.LEFT);
                                        label2.setVerticalAlignment(JLabel.CENTER);
                                        panneaux.get(element).set(0, 20);
                                        element.add(label2);
                                    } else if (panneaux.get(element).get(2) == 100 && (panneaux.get(element).get(1) == 700 || panneaux.get(element).get(1) == 0)) {
                                        JLabel label2 = ex.creerJLabel("tourNoire.svg", 100, 100);
                                        label2.setHorizontalAlignment(JLabel.LEFT);
                                        label2.setVerticalAlignment(JLabel.CENTER);
                                        panneaux.get(element).set(0, 10);
                                        element.add(label2);
                                    } else if (panneaux.get(element).get(2) == 800 && (panneaux.get(element).get(1) == 600 || panneaux.get(element).get(1) == 100)) {
                                        JLabel label2 = ex.creerJLabel("chevalBlanc.svg", 100, 100);
                                        label2.setHorizontalAlignment(JLabel.LEFT);
                                        label2.setVerticalAlignment(JLabel.CENTER);
                                        panneaux.get(element).set(0, 21);
                                        element.add(label2);
                                    } else if (panneaux.get(element).get(2) == 100 && (panneaux.get(element).get(1) == 600 || panneaux.get(element).get(1) == 100)) {
                                        JLabel label2 = ex.creerJLabel("chevalNoir.svg", 100, 100);
                                        label2.setHorizontalAlignment(JLabel.LEFT);
                                        label2.setVerticalAlignment(JLabel.CENTER);
                                        panneaux.get(element).set(0, 11);
                                        element.add(label2);
                                    } else if (panneaux.get(element).get(2) == 800 && (panneaux.get(element).get(1) == 500 || panneaux.get(element).get(1) == 200)) {
                                        JLabel label2 = ex.creerJLabel("fouBlanc.svg", 100, 100);
                                        label2.setHorizontalAlignment(JLabel.LEFT);
                                        label2.setVerticalAlignment(JLabel.CENTER);
                                        panneaux.get(element).set(0, 22);
                                        element.add(label2);
                                    } else if (panneaux.get(element).get(2) == 100 && (panneaux.get(element).get(1) == 500 || panneaux.get(element).get(1) == 200)) {
                                        JLabel label2 = ex.creerJLabel("fouNoir.svg", 100, 100);
                                        label2.setHorizontalAlignment(JLabel.LEFT);
                                        label2.setVerticalAlignment(JLabel.CENTER);
                                        panneaux.get(element).set(0, 12);
                                        element.add(label2);
                                    } else if (panneaux.get(element).get(2) == 800 && panneaux.get(element).get(1) == 400) {
                                        JLabel label2 = ex.creerJLabel("roiBlanc.svg", 100, 100);
                                        label2.setHorizontalAlignment(JLabel.LEFT);
                                        label2.setVerticalAlignment(JLabel.CENTER);
                                        panneaux.get(element).set(0, 23);
                                        element.add(label2);
                                    } else if (panneaux.get(element).get(2) == 100 && panneaux.get(element).get(1) == 300) {
                                        JLabel label2 = ex.creerJLabel("roiNoir.svg", 100, 100);
                                        label2.setHorizontalAlignment(JLabel.LEFT);
                                        label2.setVerticalAlignment(JLabel.CENTER);
                                        panneaux.get(element).set(0, 13);
                                        element.add(label2);
                                    } else if (panneaux.get(element).get(2) == 800 && panneaux.get(element).get(1) == 300) {
                                        JLabel label2 = ex.creerJLabel("reineBlanche.svg", 100, 100);
                                        label2.setHorizontalAlignment(JLabel.LEFT);
                                        label2.setVerticalAlignment(JLabel.CENTER);
                                        panneaux.get(element).set(0, 25);
                                        element.add(label2);
                                    } else if (panneaux.get(element).get(2) == 100 && panneaux.get(element).get(1) == 400) {
                                        JLabel label2 = ex.creerJLabel("reineNoire.svg", 100, 100);
                                        label2.setHorizontalAlignment(JLabel.LEFT);
                                        label2.setVerticalAlignment(JLabel.CENTER);
                                        panneaux.get(element).set(0, 15);
                                        element.add(label2);
                                    } 
                                    if (panneaux.get(element).get(3) == 0 || panneaux.get(element).get(3) == 4 ||panneaux.get(element).get(3) == 6) {
                                        element.setBackground(Color.WHITE);
                                    } else if (panneaux.get(element).get(3) == 1 || panneaux.get(element).get(3) == 3 ||panneaux.get(element).get(3) == 5 ) {
                                        element.setBackground(Color.LIGHT_GRAY);
                                    }
                                    element.revalidate();
                                    element.repaint();
                                    System.gc();
                                }
                            });
                            timer2.start();
                            timer2.setRepeats(false);
                        });
                        timer1.start();
                        timer1.setRepeats(false);
                    }
                }
            });
        }
        frame.setLayout(null);
        frame.setVisible(true);
    }
}