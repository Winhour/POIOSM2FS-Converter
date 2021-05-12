/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiosm2fs;

/**
 *
 * @author Marcin
 */
public class GLTFWriter {
    
    public String writeGLTF(String poi){
        
        String output = "";
        
        output += "{\n" +
                "  \"asset\": {\n" +
                "    \"version\": \"2.0\",\n" +
                "    \"generator\": \"ModelConverterX\"\n" +
                "  },\n" +
                "  \"scene\": 0,\n" +
                "  \"scenes\": [: {\n" +
                "    {\n" +
                "      \"nodes\": [\n" +
                "    {\n" +
                "      \"name\": \"node0\",\n" +
                "      \"children\": [\n" +
                "        1\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"node1\",\n" +
                "      \"mesh\": 0\n" +
                "    }\n" +
                "  ],\n" +
                "  \"meshes\": [\n" +
                "    {\n" +
                "      \"primitives\": [\n" +
                "        {\n" +
                "          \"attributes\": {\n" +
                "            \"POSITION\": 0,\n" +
                "            \"NORMAL\": 1,\n" +
                "            \"TEXCOORD_0\": 2\n" +
                "          },\n" +
                "          \"indices\": 3,\n" +
                "          \"mode\": 4,\n" +
                "          \"material\": 0\n" +
                "        }\n" +
                "      ],\n" +
                "      \"name\": \"part000\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"accessors\": [\n" +
                "    {\n" +
                "      \"bufferView\": 1,\n" +
                "      \"byteOffset\": 0,\n" +
                "      \"componentType\": 5126,\n" +
                "      \"count\": 4931,\n" +
                "      \"type\": \"VEC3\",\n" +
                "      \"min\": [\n" +
                "        2.59617,\n" +
                "        -1.89269,\n" +
                "        -2.25507\n" +
                "      ],\n" +
                "      \"max\": [\n" +
                "        13.53658,\n" +
                "        7.41967,\n" +
                "        -0.4867\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"bufferView\": 2,\n" +
                "      \"byteOffset\": 0,\n" +
                "      \"componentType\": 5126,\n" +
                "      \"count\": 4931,\n" +
                "      \"type\": \"VEC3\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"bufferView\": 3,\n" +
                "      \"byteOffset\": 0,\n" +
                "      \"componentType\": 5126,\n" +
                "      \"count\": 4931,\n" +
                "      \"type\": \"VEC2\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"bufferView\": 0,\n" +
                "      \"byteOffset\": 0,\n" +
                "      \"componentType\": 5125,\n" +
                "      \"count\": 14460,\n" +
                "      \"type\": \"SCALAR\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"materials\": [\n" +
                "    {\n" +
                "      \"pbrMetallicRoughness\": {\n" +
                "        \"baseColorFactor\": [\n" +
                "          1.0,\n" +
                "          1.0,\n" +
                "          1.0,\n" +
                "          1.0\n" +
                "        ],\n" +
                "        \"roughnessFactor\": 1.0,\n" +
                "        \"metallicFactor\": 0.0,\n" +
                "        \"baseColorTexture\": {\n" +
                "          \"index\": 0\n" +
                "        }\n" +
                "      },\n" +
                "      \"emissiveFactor\": [\n" +
                "        0.0,\n" +
                "        0.0,\n" +
                "        0.0\n" +
                "      ]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"textures\": [\n" +
                "    {\n" +
                "      \"source\": 0,\n" +
                "      \"name\": \"" + poi + "\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"images\": [\n" +
                "    {\n" +
                "      \"uri\": \"" + poi + ".png\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"bufferViews\": [\n" +
                "    {\n" +
                "      \"buffer\": 0,\n" +
                "      \"byteOffset\": 0,\n" +
                "      \"byteLength\": 57840\n" +
                "    },\n" +
                "    {\n" +
                "      \"buffer\": 0,\n" +
                "      \"byteOffset\": 57840,\n" +
                "      \"byteLength\": 59172,\n" +
                "      \"byteStride\": 12\n" +
                "    },\n" +
                "    {\n" +
                "      \"buffer\": 0,\n" +
                "      \"byteOffset\": 117012,\n" +
                "      \"byteLength\": 59172,\n" +
                "      \"byteStride\": 12\n" +
                "    },\n" +
                "    {\n" +
                "      \"buffer\": 0,\n" +
                "      \"byteOffset\": 176184,\n" +
                "      \"byteLength\": 39448,\n" +
                "      \"byteStride\": 8\n" +
                "    }\n" +
                "  ],\n" +
                "  \"buffers\": [\n" +
                "    {\n" +
                "      \"byteLength\": 215632,\n" +
                "      \"uri\": \"" + poi + ".bin\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        
        
        return output;
    }
    
}
